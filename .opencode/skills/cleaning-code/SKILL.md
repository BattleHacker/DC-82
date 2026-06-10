---
name: cleaning-code
description: Handles code cleanup, refactoring, and improvement tasks. Use when the user asks to clean or refactor code.
---

# Cleaning Code

Follow these rules strictly when cleaning code or parts of it:

## Rule #1 — Planning mode check
If the user is in planning mode, remind them they need to switch to build mode first. Wait until they change mode before proceeding.

## Rule #2 — Create a branch
Before any code changes, create a new branch. Name it after the cleanup (e.g., `cleanup-YYYY-MM-DD-description`). Use `git checkout -b <branch-name>`.

## Rule #3 — Create issue list
Create a list of all identified optimizations/issues before making any changes.

## Rule #4 — Create an issue context file
Create a new context file in `opencode-context/` (e.g., `cleanup-YYYY-MM-DD.md`) that lists every recognized issue. Add each issue to this file immediately as you identify it.

## Rule #5 — Work through the list
Process each issue one by one:

### 5.1 — Describe the issue
Give the user a clear description of the current issue.

### 5.2 — Offer solutions
Provide a list of predefined suggestions to solve the issue. Always include:
- An option for the user to **write their own answer**
- An option to **ask a question** before solving

### 5.3 — Mark as solved
After the issue is resolved, mark it as `[solved]` in the issue context file.

### 5.4 — Update context files
After every solved issue, update the relevant `opencode-context/` files automatically (TODOs, latest changes, etc.).

### 5.5 — Proceed
Move to the next issue in the list until all are done.

## Rule #6 — Push changes
After all issues are resolved, push the branch to remote: `git push origin <branch-name>`.
