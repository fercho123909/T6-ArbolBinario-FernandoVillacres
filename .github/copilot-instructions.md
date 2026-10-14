# Project guidance

- Keep the Java packages under `src/modelo`, `src/negocio`, and `src/app`.
- Keep node counting, leaf counting, height, and tree display recursive where appropriate.
- Measure height in edges: an empty tree is -1 and a one-node tree is 0.
- Reject insertion when the requested child position is already occupied.
- Preserve the university tree values and avoid prohibited collection types from the assignment.
- Compile with `javac -encoding UTF-8 -d out src/modelo/Nodo.java src/negocio/ArbolBinario.java src/app/Main.java`.