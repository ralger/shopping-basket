Shopping Basket
===============

## Installation

Install SBT for your environment if you don't already have it 
* [Installing sbt on Linux](https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Linux.html)
* [Installing sbt on Windows](https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Windows.html)
* [Installing sbt on macOS](https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Mac.html)

Clone the repo (code will be cloned into the current directory)

```bash
git clone https://github.com/ralger/shopping-basket 
cd shopping-basket 
```

Build and run tests
```bash
sbt clean compile test
```

Run the example program
```bash
sbt "run Apples Milk Bread"
```


