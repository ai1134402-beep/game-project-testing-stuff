# ⭐ Star Catcher — Build Your Own Java Game

**Course:** AP Computer Science A  |  **Time:** about 8–10 class periods  |  **Work:** individual or pairs

You will build a real, playable graphics game in Java. You steer a ship along the bottom of the screen, **catch falling stars** for points, and **dodge asteroids** that cost you a life. The game gets faster as your score climbs.

Along the way you will practice the core of AP CSA: **classes, constructors, instance variables, methods, `if` statements, loops, arrays, `ArrayList`, inheritance, overriding, and polymorphism.**

---

## How this project works

Your `starter/` folder is a *nearly working* program. Everything compiles and runs from the very first minute, but the game does nothing interesting yet. Your job is to find each `// TODO` comment, read the instructions, and write the code.

- 🟢 **PROVIDED** code is finished. Read it, learn from it, but you don't have to change it.
- 🟡 **TODO** comments are numbered by step (`TODO 3.2` belongs to Step 3). Work in order.
- 🔵 **CHECKPOINT** boxes tell you exactly what you should see before moving on. **Run your program at every checkpoint.** Never write more than a few lines without testing.

### The files

| File | Who writes it | What it does |
|---|---|---|
| `Main.java` | Provided | Opens the window |
| `GamePanel.java` | You + provided | The "brain": game loop, keyboard, drawing |
| `Player.java` | **You** | The ship you control |
| `FallingObject.java` | **You** | Superclass for anything that falls |
| `Star.java` | Provided (worked example) | A catchable falling object |
| `Asteroid.java` | **You, from scratch** | A dangerous falling object |

### The big idea: the game loop

Every video game is a loop that repeats about 60 times per second:

```
   ┌────────────────────────────────────────────┐
   │  Timer fires  ──►  update()  ──►  repaint() │
   │                (change data)    (redraw)    │
   └──────────────────▲──────────────────────────┘
                      └──────────── repeat ~60×/sec
```

`update()` changes numbers (positions, score, lives). `paintComponent()` just draws whatever those numbers currently say. **Keeping "change the data" separate from "draw the data" is one of the most important habits in programming.**

### How coordinates work

The origin `(0, 0)` is the **top-left** corner. `x` increases going **right**. `y` increases going **down** — so *adding* to `y` makes something fall.

```
(0,0) ───────────► x
  │
  │
  ▼
  y
```

---

## Step 1 — Launch and Customize  🟢 *Warm-up (15 min)*

**Concepts:** constants, `Color`, reading code

1. Open the project in your IDE and run `Main`. You should see a dark blue window. Nothing else happens yet — that's expected!
2. Find **TODO 1.1** in `GamePanel.java`. Change `BACKGROUND_COLOR` to a color you like. `new Color(r, g, b)` takes three values from 0 to 255.
3. Read through all of `GamePanel.java`. Don't worry about understanding everything. Find and note in your notebook:
   - Which method runs 60 times per second?
   - Which variables store the game's data?
   - Where is `Player` created?

> 🔵 **CHECKPOINT 1:** A window opens with your custom background color.

**Think about it:** `BACKGROUND_COLOR` is declared `static final`. What does `final` prevent? Why is that a good idea for a value like the window width?

---

## Step 2 — The Player Class  🟡 *(45–60 min)*

**Concepts:** classes, `private` instance variables, constructors, methods, objects

A **class** is a blueprint. An **object** is one thing built from that blueprint. `GamePanel` already contains the line:

```java
player = new Player(WIDTH / 2 - 40, HEIGHT - 60);
```

That calls the `Player` **constructor** to build one `Player` object. Right now `Player` is empty, so nothing shows up. Open `Player.java` and complete:

| TODO | Task |
|---|---|
| **2.1** | Declare the six `private int` instance variables |
| **2.2** | Initialize them in the constructor (`x` and `y` come from the parameters) |
| **2.3** | Draw the ship with `g.setColor(...)` and `g.fillRect(...)` |
| **2.4** | Make `getBounds()` return a `Rectangle` of the ship's real position and size |

Two tips:

- Inside the constructor, you will write things like `x = startX;`. The parameter and the instance variable have different names on purpose.
- `g.fillRect(left, top, width, height)` draws a filled rectangle. Layer two rectangles for a body and a cockpit.

> 🔵 **CHECKPOINT 2:** Your ship appears near the bottom-center of the window.

**Think about it:**
1. Why are the instance variables `private`? What would be risky about making them `public`?
2. If you create two `Player` objects, how many `x` variables exist? Where?

---

## Step 3 — Moving the Ship  🟡 *(45–60 min)*

**Concepts:** `if` statements, boolean variables, compound conditions (`||`), relational operators, method calls between objects

Movement is a three-part chain:

```
keyboard  ──►  leftPressed / rightPressed  ──►  player.setDirection()  ──►  player.update()
(KeyEvent)        (booleans in GamePanel)         (stores -1, 0, or 1)       (changes x)
```

**In `Player.java`:**

| TODO | Task |
|---|---|
| **3.1** | `setDirection` stores its parameter in `direction` |
| **3.2** | `update` moves the ship: `x += direction * speed;` |
| **3.3** | Two `if` statements keep the ship inside the window |

For 3.3, remember the ship's **right edge** is `x + width`, not `x`.

**In `GamePanel.java`:**

| TODO | Task |
|---|---|
| **3.4** | In `update()`, compute `direction` from the two booleans, then call the player's methods |
| **3.5** | In `keyPressed`, set `leftPressed` / `rightPressed` to `true` |
| **3.6** | In `keyReleased`, set them back to `false` |

Accept both arrow keys **and** `A` / `D` using `||`:

```java
if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A)
```

> 🔵 **CHECKPOINT 3:** The ship slides smoothly left and right, stops when you let go, and can **never** leave the window. Hold *both* keys at once — what happens? (Check TODO 3.4's question.)

**Debug it:** Remove your boundary `if` statements and run again. What happens to the ship? Put them back.

---

## Step 4 — The FallingObject Superclass  🟡 *(30–45 min)*

**Concepts:** superclass design, methods, `boolean` return values, `Rectangle`

Stars and asteroids both fall, both have a position and size, and both can be caught. Instead of writing that twice, we put the shared code in one **superclass**, `FallingObject`. Open it and finish:

| TODO | Task |
|---|---|
| **4.1** | `update()` — add `speed` to `y` |
| **4.2** | `isOffScreen(panelHeight)` — return `true` if `y` is past the bottom |
| **4.3** | `getBounds()` — return a `Rectangle` using `x`, `y`, `size`, `size` |

Now study `Star.java` (already written). It's your worked example for inheritance. Notice:

- `extends FallingObject` — a Star **is-a** FallingObject
- `super(x, 30, speed)` — the first line of a subclass constructor runs the superclass constructor
- `@Override public void draw(...)` — Star replaces the superclass's white-square `draw` with its own star shape
- `getX()`, `getY()`, `getSize()` — Star can't see `FallingObject`'s `private` variables, so it uses these **accessor methods**

**Test it** (temporary):

- In `GamePanel`'s constructor, uncomment the `objects.add(new Star(280, 2));` line (**TODO 4.4**).
- In `update()`, write the enhanced `for` loop from **TODO 4.5**.

> 🔵 **CHECKPOINT 4:** One yellow star falls from the top and drops off the bottom. (It starts above the window, so wait a moment.)

**Think about it:** `FallingObject.draw` draws a white square, but we never see a white square. Why not?

---

## Step 5 — Many Objects: Arrays and ArrayList  🟡 *(60–75 min)*

**Concepts:** `ArrayList`, arrays, `for` loops, `Math.random`, casting, traversal with removal

### 5A — A starfield with arrays *(TODO 5.1, 5.2)*

The twinkling dots in the background are stored in two **parallel arrays**: `bgX[i]` and `bgY[i]` together describe star number `i`.

- **5.1** Fill both arrays with random positions inside `startNewGame()`.
- **5.2** In `update()`, move every dot down, and wrap it to the top when it passes the bottom.

`(int) (Math.random() * WIDTH)` gives a random integer from `0` to `WIDTH - 1`. Be ready to explain why.

> 🔵 **CHECKPOINT 5A:** Dots scroll down the screen at different speeds, like flying through space.

### 5B — Spawning stars *(TODO 5.3)*

Complete `spawnObject()` so it makes a `Star` with a random `x` and speed and adds it to the `ArrayList`. `update()` already calls this method every so often. (Comment out the temporary test star from Step 4.)

### 5C — Cleaning up with a backwards loop *(TODO 5.4)*

Objects that fall off the bottom must be **removed**, or the list grows forever and the game slows down.

> ⚠️ **The classic bug.** Suppose you loop forward and remove item 2. Item 3 slides into position 2, then your loop moves on to position 3 and **skips it**. Counting **backwards** avoids this.

Replace your enhanced `for` loop with a backwards counting loop, as the TODO describes.

*Experiment:* Temporarily change it to a forward loop and watch for stars that freeze or flicker. Then fix it again.

> 🔵 **CHECKPOINT 5:** Stars rain down continuously. To prove the cleanup works, temporarily add `System.out.println(objects.size());` inside `update()`. The number should rise and fall, **never** growing without end. Remove the print when done.

---

## Step 6 — Asteroids, Collisions, Scoring  🟡 *(60–90 min)*

**Concepts:** inheritance (writing a subclass), method overriding, polymorphism, collision detection, `Rectangle.intersects`

### 6A — Write `Asteroid` yourself *(you do, from scratch)*

Open `Asteroid.java`. It contains only a comment describing what to write. Delete the comment and write the entire class, using `Star.java` as your model:

1. `public class Asteroid extends FallingObject`
2. A constructor that calls `super(...)` with size **40**
3. An `@Override` of `draw` — a gray circle with a few dark craters
4. An `@Override` of `isHazard` that returns `true`

### 6B — Spawn them *(TODO 6.1)*

Update `spawnObject()` so 30% of new objects are asteroids and the rest are stars. Notice that **both** go into the *same* `ArrayList<FallingObject>`.

### 6C — Collisions *(TODO 6.2)*

Every `Rectangle` has an `intersects` method that returns `true` if two rectangles overlap:

```java
if (obj.getBounds().intersects(player.getBounds()))
```

Inside, decide what happened:

- Hazard → lose a life
- Otherwise → gain **10** points
- Either way, remove the object

> 🔵 **CHECKPOINT 6:** Catching stars raises your score. Hitting an asteroid drops your lives. Asteroids and stars look different. (Lives can go negative for now — Step 7 fixes that.)

### 🌟 The polymorphism moment

Look at this line in your loop:

```java
if (obj.isHazard())
```

The variable `obj` has type `FallingObject`, but the actual object might be a `Star` or an `Asteroid`. Java decides **at run time** which `isHazard()` to run. The same goes for `obj.draw(g)`. Your game loop never says "if it's a star do this, if it's an asteroid do that" — each object knows how to behave. That's **polymorphism**, and it's what makes adding a new object type (say, a `Shield`) easy.

---

## Step 7 — Game Over, Restart, and Levels  🟡 *(30 min)*

**Concepts:** `&&`, integer division, state variables

| TODO | Task |
|---|---|
| **7.1** | When `lives <= 0`, set `gameOver` to `true` |
| **7.2** | Calculate `level` as `score / 100 + 1` |
| **7.3** | In `keyPressed`, restart with `startNewGame()` if the game is over **and** `R` was pressed |

> 🔵 **CHECKPOINT 7 — FINAL:** The game ends at 0 lives, shows GAME OVER and your score, restarts on `R`, and gets noticeably harder as the level rises.

**Think about it:** At what exact scores does the level change? Test `99`, `100`, and `199` by hand using integer division *before* you run the program.

---

## Step 8 — Make It Yours  🌟 *(choose at least two)*

Choose challenges from at least two different levels. Each one is a chance to practice something real.

**⭐ Level 1 — Tweak**
- Change colors, sizes, speeds, point values, number of lives, spawn percentages.
- Redesign the ship or asteroid art using `fillOval`, `fillPolygon`, or `drawLine`.

**⭐⭐ Level 2 — Extend**
- **Bonus star:** Create `GoldStar extends FallingObject` worth 50 points that falls faster. (Uses a *new* subclass, so `GamePanel` barely changes!)
- **Heart:** Create a `Heart` class that gives the player an extra life.
- **High score:** Track the best score across restarts with a new instance variable.
- **Lives display:** Draw small ship icons for lives instead of text, using a `for` loop.

**⭐⭐⭐ Level 3 — Engineer**
- **Missed-star penalty:** Lose points when a star falls off the bottom (hint: where in your loop do you know that happened?).
- **Shield power-up:** Make the player immune to asteroids for 5 seconds. (You'll need to count frames.)
- **Shooting:** Add a `Laser` class and let the player destroy asteroids with the spacebar.
- **Refactor challenge:** Add `getPoints()` to `FallingObject` (default `0`), override it in each subclass, and remove the `if (obj.isHazard())` branch for scoring. Which design is better, and why?
- **2D array:** Build a high-score table or a tile-based background using a 2D array.

---

## Check Your Understanding  📝

Answer these in your notebook. Use your own code as evidence. These are the kinds of questions you'll see on the AP exam.

1. **Classes vs. objects.** Using `Player` as an example, explain the difference. How many objects of each class exist while the game runs?
2. **Encapsulation.** `Star` draws itself using `getX()` instead of `x`. Why can't it use `x` directly, and why did we design it this way?
3. **Constructors.** Explain what `super(x, 30, speed)` does. What error would you get if you left it out of `Star`?
4. **Trace.** Given `score = 90`, a star is caught, then another. Trace `score` and `level` after each catch.
5. **Arrays vs. `ArrayList`.** The background stars use arrays, but falling objects use an `ArrayList`. Why is each the right choice?
6. **Traversal.** Explain in your own words why we loop backwards when removing from an `ArrayList`. Give a concrete example with a list of 4 elements.
7. **Polymorphism.** The list is declared `ArrayList<FallingObject>`. How can it hold `Star`s and `Asteroid`s? When `obj.draw(g)` runs, how does Java know which `draw` to call?
8. **Debugging.** Your ship moves off the right edge of the screen. Which method contains the bug? Which line? What would the fix look like?

### AP-style free response practice

> Write a class `GoldStar` that extends `FallingObject`. A `GoldStar` is size 24, worth bonus points, and is drawn as a gold-colored circle. Write the complete class, including the constructor and any methods you override.
>
> Then write a method `countHazards()` for `GamePanel` that returns the number of hazards in `objects`.

---

## Troubleshooting

| Symptom | Likely cause |
|---|---|
| `cannot find symbol` | Typo, missing `import`, or a variable used outside the method where it was declared |
| `incompatible types: possible lossy conversion from double to int` | `Math.random()` returns a `double` — cast with `(int)` |
| `constructor FallingObject cannot be applied` | Your subclass constructor is missing `super(...)` |
| Ship doesn't move | Click the window once to give it focus; check TODO 3.4–3.6 |
| Ship disappears | Check `getBounds`/`draw` use the **instance variables**, not `0` |
| Nothing falls | Did you write TODO 4.1 (`update`) and TODO 5.3 (`spawnObject`)? |
| `IndexOutOfBoundsException` | Your loop starts at `objects.size()` instead of `objects.size() - 1` |
| Objects freeze or skip | You're removing inside a forward loop |

**Debugging habit:** when something is wrong, add `System.out.println(...)` to print the variable you suspect, run, and *look at the numbers* before changing any code.

---

## Submission checklist

- [ ] Game runs with no errors from start to GAME OVER and restart
- [ ] All TODOs completed (search for `TODO` — none left, except any you chose to skip in Step 8)
- [ ] At least **two** Step 8 challenges completed
- [ ] Your own comments explain each method you wrote
- [ ] "Check Your Understanding" answers completed
