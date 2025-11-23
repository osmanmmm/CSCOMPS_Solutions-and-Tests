#  How to Build & Run

> Run commands **from the repo root**. The program expects the CSV at `data/assignment_b1.csv`.

---

## Step 1 — Confirm Java 17

Check your Java version:

```bash
java -version
```

You should see something like:

```
java version "17.x"
```

If not (macOS / Linux), set your environment and re-open your terminal:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH="$JAVA_HOME/bin:$PATH"
```

---

## Step 2 — Build the project

```bash
mvn -q clean compile
```

---

## Step 3 — Run each mode (via Maven)

**LIFO** — Last Hired, First Fired (Stack):

```bash
mvn -q exec:java -Dexec.mainClass=com.cs.comps.MainKt -Dexec.args="lifo"
```

**FIFO** — First Hired, First Fired (Queue):

```bash
mvn -q exec:java -Dexec.mainClass=com.cs.comps.MainKt -Dexec.args="fifo"
```

**PRIORITY** — Min-heap policy:

```bash
mvn -q exec:java -Dexec.mainClass=com.cs.comps.MainKt -Dexec.args="priority"
```

**Optional** — Print all loaded employees (no scoring):

```bash
mvn -q exec:java -Dexec.mainClass=com.cs.comps.MainKt -Dexec.args="dump"
```

**Run all policies in sequence**:

```bash
mvn -q exec:java -Dexec.mainClass=com.cs.comps.MainKt -Dexec.args="all"
```

---

## Notes 

* In the starter version, `lifo`, `fifo`, and `priority` will **fail intentionally** with messages like:

  * `An operation is not implemented: push not implemented`
  * `An operation is not implemented: enqueue not implemented`
  * `An operation is not implemented: sortEmployeesByPriority not fully implemented`
* `dump` **should** work and list all employees (with `performanceScore=null`).

---

## Troubleshooting

* **“parameters 'mainClass' … missing or invalid”**
  Make sure you include `-Dexec.mainClass=com.cs.comps.MainKt` exactly as shown above.
* **CSV not found**
  Ensure you’re running from the repo root so `data/assignment_b1.csv` is resolvable.