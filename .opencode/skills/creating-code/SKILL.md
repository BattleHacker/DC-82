---
name: creating-code
description: Handles code creation tasks when in build mode. Use when the user asks to implement or generate new code.
---

# Creating Code

Follow these rules strictly for every code change:

## Rule #1 — Ask before acting
Never change code on your own. Always ask for the user's go-ahead first.

## Rule #2 — Suggest better solutions
If you think of a better approach than what the user described, suggest it before implementing.

## Rule #3 — Comments
Add short, clear comments to the code. Do not comment self-explanatory code.

## Rule #4 — Update context after changes
After every code change, update the relevant files in `opencode-context/`. If the user mentioned any TODOs in their prompt, add them to the corresponding context file's TODOs section.

## Rule #5 — English naming
All code naming (variables, methods, classes, packages, parameters) must be in English.
