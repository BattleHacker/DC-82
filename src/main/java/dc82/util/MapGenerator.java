package dc82.util;

import dc82.model.HexCoord;
import dc82.model.MapState;
import dc82.model.Milestone;
import dc82.model.Path;

import java.util.*;

public final class MapGenerator {

    private static final Random RAND = new Random();
    private static final float MIN_NEIGHBOR_ANGLE = 30f;

    private MapGenerator() {}

    public static void generateNeighbors(MapState state) {
        HexCoord currentHex = state.current;
        Milestone current = state.milestones.get(currentHex);

        List<HexCoord> existing = new ArrayList<>();
        for (Path p : current.paths) {
            existing.add(p.other(currentHex));
        }

        boolean isFirst = state.allPaths.isEmpty() && currentHex.equals(HexCoord.ORIGIN);
        int target = isFirst ? 4 : RAND.nextInt(5) + 1;

        if (target <= existing.size()) {
            boolean canDeadEnd = false;
            if (!existing.isEmpty()) {
                Milestone prev = state.milestones.get(existing.get(0));
                if (prev != null && countUnvisitedNeighbors(state, prev) >= 2) {
                    canDeadEnd = true;
                }
            }
            if (!canDeadEnd) {
                target = existing.size() + 1;
            }
            if (target <= existing.size()) return;
        }

        int remaining = target - existing.size();
        List<HexCoord> selected = new ArrayList<>();
        List<HexCoord> combined = new ArrayList<>(existing);
        int newCount = 0;
        int existingCount = 0;
        List<HexCoord> pool = currentHex.hexesInRange(1, 3);

        // Phase 1: first new milestone — roughly away from center
        if (remaining > 0) {
            HexCoord expansion = findExpansionCandidate(state, currentHex, combined, pool);
            if (expansion != null) {
                selected.add(expansion);
                combined.add(expansion);
                newCount++;
                remaining--;
            }
        }

        // Phase 2: fill remaining slots
        while (remaining > 0) {
            HexCoord candidate = null;

            if (existingCount < 2 && newCount > 0) {
                candidate = findExistingCandidate(state, currentHex, combined, pool);
            }

            if (candidate == null) {
                candidate = findNewCandidate(state, currentHex, combined, pool);
            }

            if (candidate == null) break;

            selected.add(candidate);
            combined.add(candidate);
            if (state.milestones.containsKey(candidate)) {
                existingCount++;
            } else {
                newCount++;
            }
            remaining--;
        }

        for (HexCoord hex : selected) {
            Milestone ms = state.milestones.get(hex);
            if (ms == null) {
                ms = new Milestone(hex);
                state.milestones.put(hex, ms);
            }
            if (!hasPath(state, currentHex, hex)) {
                Path p = new Path(currentHex, hex);
                state.allPaths.add(p);
                current.paths.add(p);
                ms.paths.add(p);
            }
        }
    }

    private static HexCoord findExpansionCandidate(MapState state, HexCoord current,
                                                     List<HexCoord> combined,
                                                     List<HexCoord> pool) {
        pool = new ArrayList<>(pool);
        Collections.shuffle(pool, RAND);

        boolean useDirection = !current.equals(HexCoord.ORIGIN);
        float targetAngle = 0;
        if (useDirection) {
            targetAngle = normalizeAngle(current.angleDegTo(HexCoord.ORIGIN) + 180f);
        }

        // First pass: only candidates roughly away from center
        for (HexCoord h : pool) {
            if (state.milestones.containsKey(h)) continue;
            if (isOnAnyPathLine(state, h)) continue;
            if (!isValidPath(state, current, h)) continue;
            if (!angleOkWithAll(current, h, combined, MIN_NEIGHBOR_ANGLE)) continue;
            if (useDirection) {
                float hexAngle = normalizeAngle(current.angleDegTo(h));
                float diff = Math.abs(normalizeAngle(hexAngle - targetAngle));
                if (diff > 180f) diff = 360f - diff;
                if (diff > 20f) continue;
            }
            return h;
        }

        // Second pass: any direction (fallback)
        if (useDirection) {
            for (HexCoord h : pool) {
                if (state.milestones.containsKey(h)) continue;
                if (isOnAnyPathLine(state, h)) continue;
                if (!isValidPath(state, current, h)) continue;
                if (!angleOkWithAll(current, h, combined, MIN_NEIGHBOR_ANGLE)) continue;
                return h;
            }
        }

        return null;
    }

    private static HexCoord findExistingCandidate(MapState state, HexCoord current,
                                                    List<HexCoord> combined,
                                                    List<HexCoord> pool) {
        pool = new ArrayList<>(pool);
        Collections.shuffle(pool, RAND);
        for (HexCoord h : pool) {
            if (combined.contains(h)) continue;
            Milestone ms = state.milestones.get(h);
            if (ms == null || ms.visited) continue;
            if (!isValidPath(state, current, h)) continue;
            if (!angleOkWithAll(current, h, combined, MIN_NEIGHBOR_ANGLE)) continue;
            return h;
        }
        return null;
    }

    private static HexCoord findNewCandidate(MapState state, HexCoord current,
                                               List<HexCoord> combined,
                                               List<HexCoord> pool) {
        pool = new ArrayList<>(pool);
        Collections.shuffle(pool, RAND);
        for (HexCoord h : pool) {
            if (state.milestones.containsKey(h)) continue;
            if (isOnAnyPathLine(state, h)) continue;
            if (combined.contains(h)) continue;
            if (!isValidPath(state, current, h)) continue;
            if (!angleOkWithAll(current, h, combined, MIN_NEIGHBOR_ANGLE)) continue;
            return h;
        }
        return null;
    }

    private static int countUnvisitedNeighbors(MapState state, Milestone ms) {
        int count = 0;
        for (Path p : ms.paths) {
            HexCoord other = p.other(ms.hex);
            Milestone neighbor = state.milestones.get(other);
            if (neighbor != null && !neighbor.visited) count++;
        }
        return count;
    }

    private static boolean isOnAnyPathLine(MapState state, HexCoord hex) {
        for (Path p : state.allPaths) {
            if (hex.distanceTo(p.a) > 3 && hex.distanceTo(p.b) > 3) continue;
            List<HexCoord> line = p.a.lineTo(p.b);
            for (int i = 1; i < line.size() - 1; i++) {
                if (line.get(i).equals(hex)) return true;
            }
        }
        return false;
    }

    private static boolean isValidPath(MapState state, HexCoord from, HexCoord to) {
        for (Path p : state.allPaths) {
            if (p.connects(from) || p.connects(to)) continue;
            if (segmentsCross(from, to, p.a, p.b)) return false;
        }

        List<HexCoord> line = from.lineTo(to);
        for (HexCoord h : line) {
            if (h.equals(from) || h.equals(to)) continue;
            if (state.milestones.containsKey(h)) return false;
        }
        return true;
    }

    private static boolean segmentsCross(HexCoord a, HexCoord b, HexCoord c, HexCoord d) {
        float o1 = orient(a, b, c);
        float o2 = orient(a, b, d);
        float o3 = orient(c, d, a);
        float o4 = orient(c, d, b);

        if (o1 * o2 < 0f && o3 * o4 < 0f) return true;

        if (Math.abs(o1) < 0.001f && onSegment(a, b, c)) return false;
        if (Math.abs(o2) < 0.001f && onSegment(a, b, d)) return false;
        if (Math.abs(o3) < 0.001f && onSegment(c, d, a)) return false;
        if (Math.abs(o4) < 0.001f && onSegment(c, d, b)) return false;

        return false;
    }

    private static float orient(HexCoord p, HexCoord q, HexCoord r) {
        return (q.pixelX() - p.pixelX()) * (r.pixelY() - p.pixelY())
             - (q.pixelY() - p.pixelY()) * (r.pixelX() - p.pixelX());
    }

    private static boolean onSegment(HexCoord a, HexCoord b, HexCoord c) {
        float minX = Math.min(a.pixelX(), b.pixelX()) - 0.001f;
        float maxX = Math.max(a.pixelX(), b.pixelX()) + 0.001f;
        float minY = Math.min(a.pixelY(), b.pixelY()) - 0.001f;
        float maxY = Math.max(a.pixelY(), b.pixelY()) + 0.001f;
        return c.pixelX() >= minX && c.pixelX() <= maxX
            && c.pixelY() >= minY && c.pixelY() <= maxY;
    }

    private static boolean hasPath(MapState state, HexCoord a, HexCoord b) {
        for (Path p : state.allPaths) {
            if (p.a.equals(a) && p.b.equals(b)) return true;
            if (p.a.equals(b) && p.b.equals(a)) return true;
        }
        return false;
    }

    private static boolean angleOkWithAll(HexCoord from, HexCoord candidate,
                                          List<HexCoord> selected, float minAngle) {
        float ca = from.angleDegTo(candidate);
        for (HexCoord s : selected) {
            float sa = from.angleDegTo(s);
            float diff = Math.abs(normalizeAngle(ca - sa));
            if (diff > 180f) diff = 360f - diff;
            if (diff < minAngle) return false;
        }
        return true;
    }

    private static float normalizeAngle(float a) {
        a = a % 360f;
        if (a < 0) a += 360f;
        return a;
    }
}
