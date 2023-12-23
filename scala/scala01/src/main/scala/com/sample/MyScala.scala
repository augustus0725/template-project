package com.sample

import collection.JavaConverters._

object MyScala {
  def valvar(): Unit = {
    // value -> val, 这个定义的变量值不可变
    val x: Int = 5
    println(x)
    // x = 6 => 编译器就会报错

    // variable -> var, 这个定义的值是可变的
    var y: Int = 100
    y = 200
    println(y)

    // 类型推导
    val z = 100
    println(z)

    val a = 1.0
    println(a)

    val b = 'h'
    println(b)

    val c = "hello world"
    println(c)

    // 特殊的变量名称
    val `a.b` = 20
    println(`a.b`)

    val π = 3.1415926
    println(π)

    val `50cent` = "50cent"
    println(`50cent`)

    // 核心数值类型
    // Byte Short Int Float Long Double

    // 字符串
    val equal = "one two three" == "one two three"
    println(equal)

    // ==> 3重引号的字符串
    val bigstr =
      """one two \t \n three
        |        four
        |        """.stripMargin
    println(bigstr)

    // ==> 使用外部数据
    println(s"ni hao, $π")
    println(s"ni hao, ${π}")
    println(s"ni hao, ${π * 3}")
    println(s"ni hao, ${`50cent` * 3}")

    // ==> 需要格式化字符串 printf的风格
    println(f"ni hao, ${`50cent`}%.3s")
    println(f"ni hao, ${π}%.2f")

    // 正则表达式
    val text = "apple 3.14159"
    val pattern = """apple ([\d.]+)""".r
    val pattern(value) = text
    println(value)
    println(value.toDouble)

    // 强制类型转换
    val ll = 'a'.asInstanceOf[Long] // 尽量避免使用, 会有运行时错误
    println(ll)
    println(ll.getClass)
    println(ll.isInstanceOf[Long])
    println(ll.toByte.getClass)
    println((3.0 / 4.0).toString)

    // 元组
    val info = (1, "sabo")
    println(info)
    println(info._1 + ": " + info._2)
    println(info.getClass)

    val red = "red" -> (255, 0, 0)
    println(red.getClass)
    println(red._1 + ":" + red._2)
  }

  def expressions(): Unit = {
    val amount = {
      val x = 100;
      x + 10
    } // ==> 表达式块
    println(amount) // => 110

    val amount1 = {
      val x = 2
      x + 10
    }
    println(amount1)

    val x = 100
    val y = 200
    println(if (x > y) x else y)
    if (x > y) {
      println("x > y")
    } else {
      println("x <= y")
    }

    // switch case
    println(x > y match {
      case true => "x > y"
      case false => "x <= y"
    })

    val day = "xxx"
    println(day match {
      case "MON" | "TUE" => {
        "workday"
      }
      case "SAT" | "SUN" => {
        "weekend"
      }
      case other => {
        "unknown"
      }
    })

    // 循环
    for (x <- 1 to 7) {
      println(x)
    }

    val vec01 = for (x <- 1 to 12) yield x
    println(vec01.getClass)
    println(vec01)

    for (v <- vec01) {
      println(v)
    }

    for {i <- 1 to 3
         j <- 1 to 3} {
      println(i + ": " + j)
    }

    var loop = 3
    while (loop > 0) {
      println(loop)
      loop -= 1
    }
  }

  // scala 里最重要的函数
  // 纯函数
  def hi = "hi"

  def hi1 = {
    "hi1"
  }

  def mulxy(x: Int, y: Int): Int = {
    x * y
  }

  def mulxy1(x: Int, y: Int) = x * y

  // 首类函数 first-class
  // 函数可以当类型用
  def fun1(x: Int): Int = 2 * x

  def thisfun(): Unit = {
    val fun2: (Int) => Int = fun1

    println(fun2(333))
  }

  // 高级函数
  def safeString(s: String, f: String => String) = {
    if (s != null) f(s) else s
  }

  // 偏函数

  // Collections
  def collectionsDemo(): Unit = {
    val numbers = List(1, 2, 3, 4, 2)
    numbers.foreach((v: Int) => println(v))
    val numbers2 = numbers.map((v: Int) => v + 1)
    val total = numbers2.reduce((l: Int, r: Int) => l + r)
    println("total : " + total)
    val setNumbers = Set(numbers)
    println(setNumbers)

    // cons 操作符
    val numbers3 = 1 :: 2 :: 3 :: Nil
    println(numbers3)

    val numbers4 = 5 :: numbers3
    println(numbers4)

    val numbers5 = numbers4 :: 6 :: Nil // ==> List(List(5, 1, 2, 3), 6)
    println(numbers5)

    val partitions = numbers3 partition (_ < 3)
    println(partitions)
    println(numbers3 slice(1, 3)) // ==> List(2, 3)
    println(List(1, 2) zip List("one", "two"))

    println(List(1, 2, 3).mkString(","))

    // Java 和 Scala的集合转换
    println(List(1, 2, 3).asJava)
    println(List(1, 2, 3).asJava.asScala.toList)

    // List   Set Map 不可变集合 collection.immutable.*
    // Buffer Set Map 可变集合   collection.mutable.*

    val nums = collection.mutable.Buffer(1)
    for (i <- 2 to 10) nums += i
    println(nums)
    val nums1 = collection.mutable.Buffer[Int]()
    for (i <- 1 to 10) nums1 += i
    println(nums1)
    // 可变 ->  不可变
    println(nums1.toList)
    // 不可变 -> 可变
    println(nums.toList.toBuffer)

  }

  // 面向对象
  class User (n: String) {
    val name = n

    override def toString: String = s"Hello: ${name}"
  }

  class A() {
    def hi = "hi: A"
  }

  class B() extends A {
    override def hi: String = "hello: B"
  }

  // 抽象类
  abstract class Car {
    def color: String;
  }

  class RedCar extends Car {
    override def color: String = "red"
    def apply(): Unit = {
      println("apply invoked...")
    }
  }

  // 匿名类
  abstract class Listener {
    def trigger;
  }
  // object 类, 简单实现单例的方案
  object Main {
    def invoke = {
      println("It's singleton")
    }
  }

  // case 类
  case class Character(name: String, bad: Boolean)

  // trait 多继承的方案
  trait Base {
    override def toString: String = "Base"
  }

  trait BA extends Base {
    override def toString: String = "A->" + super.toString
  }

  trait BB extends Base {
    override def toString: String = "B->" + super.toString
  }

  trait BC extends Base {
    override def toString: String = "C->" + super.toString
  }

  class D extends BA with BB with BC {
    override def toString: String = "D->" + super.toString
  }

  def testClass():Unit = {
    val user = new User("sabo")
    println(user)

    val b = new B()
    println(b.hi)

    val car = new RedCar
    println(car.color)
    car()  // ==> 默认会调用apply方法

    val listener = new Listener {
      override def trigger: Unit = {
        println("It's a listener...")
      }
    }

    listener.trigger

    // 测试object类
    Main.invoke

    // 测试case类
    val g = Character("sabo", true)
    val b1 = g.copy(name = "sabo2")

    println(g match {
      case Character(x, true) => s"$x is good"
      case Character(x, false) => s"$x is bad"
    })

    // 测试 trait
    println(new D)
  }

  def main(args: Array[String]): Unit = {
    valvar()
    expressions()
    println(hi1)
    println(mulxy(1, 2))
    println(mulxy1(2, 2))
    thisfun()
    collectionsDemo()
    testClass()
  }
}
