package dc82.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HexCoord {

    public static final HexCoord ORIGIN = new HexCoord(0, 0);
    private static final float PIXEL_X_FACTOR = 1.5f;
    private static final float SQRT3 = 1.73205f;
    private static final float SQRT3_HALF = 0.866025f;

    public final int q;
    public final int r;

    public HexCoord(int q, int r) {
        this.q = q;
        this.r = r;
    }

    public int distanceTo(HexCoord other) {
        int dq = Math.abs(q - other.q);
        int dr = Math.abs(r - other.r);
        int ds = Math.abs((q + r) - (other.q + other.r));
        return (dq + dr + ds) / 2;
    }

    public float angleDegTo(HexCoord other) {
        float dx = other.pixelX() - pixelX();
        float dy = other.pixelY() - pixelY();
        return (float) Math.toDegrees(Math.atan2(dy, dx));
    }

    public List<HexCoord> hexesInRange(int minDist, int maxDist) {
        List<HexCoord> result = new ArrayList<>();
        for (int dq = -maxDist; dq <= maxDist; dq++) {
            for (int dr = -maxDist; dr <= maxDist; dr++) {
                int ds = -dq - dr;
                if (Math.abs(ds) > maxDist) continue;
                HexCoord h = new HexCoord(q + dq, r + dr);
                int dist = distanceTo(h);
                if (dist >= minDist && dist <= maxDist) {
                    result.add(h);
                }
            }
        }
        return result;
    }

    public List<HexCoord> lineTo(HexCoord other) {
        int N = distanceTo(other);
        List<HexCoord> result = new ArrayList<>();
        float step = 1f / Math.max(N, 1);
        for (int i = 0; i <= N; i++) {
            float t = i * step;
            float qf = q + (other.q - q) * t;
            float rf = r + (other.r - r) * t;
            result.add(hexRound(qf, rf));
        }
        return result;
    }

    public float pixelX() {
        return q * PIXEL_X_FACTOR;
    }

    public float pixelY() {
        return r * SQRT3 + q * SQRT3_HALF;
    }

    private static HexCoord hexRound(float qf, float rf) {
        float sf = -qf - rf;
        int qr = Math.round(qf);
        int rr = Math.round(rf);
        int sr = Math.round(sf);

        float qDiff = Math.abs(qr - qf);
        float rDiff = Math.abs(rr - rf);
        float sDiff = Math.abs(sr - sf);

        if (qDiff > rDiff && qDiff > sDiff) {
            qr = -rr - sr;
        } else if (rDiff > sDiff) {
            rr = -qr - sr;
        }
        return new HexCoord(qr, rr);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HexCoord h)) return false;
        return q == h.q && r == h.r;
    }

    @Override
    public int hashCode() {
        return Objects.hash(q, r);
    }

    @Override
    public String toString() {
        return "(" + q + "," + r + ")";
    }
}
