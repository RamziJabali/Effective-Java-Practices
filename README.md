# Table of Contents  
- [Creating & Destroying Objects](#1-creating--destroying-objects)
  - [Item 1:](#item-1)
  - [Item 2:](#item-2)
    - [Consider a builder when faced with many constructor parameters](#consider-a-builder-when-faced-with-many-constructor-parameters)
  - [Item 3:](#item-3)
  
# Creating & Destroying Objects
# Item 1:

### What is a Static Factory Method: Simply a static method that returns an instant of the class.

Advantages:

1. Unlike constructors they have names.
2. Different signatures.
3. Unlike constructors, they are not required to make a new Object everytime they are invoked.
5. Unlike constructors, they can return an object of any subtype of their return type.

Disadvantages:

1. Only disadvantage of providing static factory methods is that classes without public or protected 
constructors cannot be subclassed.

2. They are not readily distinguishible from other static methods.


Example:
```
 private CoordinatesStaticFactoryMethod(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
  public static final CoordinatesStaticFactoryMethod fromXY(double x, double y) {
        return new CoordinatesStaticFactoryMethod(x, y);
    }

   public static final CoordinatesStaticFactoryMethod fromAngles(double angle, double distance) {
       return new CoordinatesStaticFactoryMethod(distance * Math.cos(angle), distance * Math.sin(angle));
    }
```
# Item 2:
### Consider a builder when faced with many constructor parameters

Static factories and constructors share a limitation: they do not scale well to large 
numbers of optional parameters.

Example:
```
    public boolean isUserEnteringRow;
    public boolean isUserEnteringColumn;
    public boolean startOfGame;
    public boolean didCurrentUserMiss;
    public boolean didCurrentUserHitOwnShip;
    public boolean didCurrentUserHitEnemyShip;
    public boolean isShipAlreadyHit;
```
Traditionally, programmers have used `Telescoping constructors` pattern, in which you provide a contructor
with only the required parameters, another with single optional parameters, a third with two optional parameters,
and so on, culminating in a constructor with all the optional paramters.

The telescoping constructor patterens work, but it is hard to write client code when they are many parameters, and harder still to read it.

### A Second alternative when facing many contructor parameters is the JavaBeans pattern

In which you call a parametersless contructor to create the object and call the setter and getter methods
to set each required parameter and each optional parameter of interest.

```
//JavaBeans Pattern - allows inconsistency, mandates mutability

public class NutritionFacts{
  //parameters initialized to default values(if any)
  private int servingSize = -1;//Required: no default values (if any)
  private int servings = -1;//Required: no default values (if any)
  private int calories = 0;
  private int fat = 0;
  private int sodium = 0;
  private int carbohydrates = 0;
}

public NutritionFacts(){}

public void setServingSize(int val){
servingSize = val;
}
"                                 "
```
Unfortunately, the JavaBeans pattern has serious disadvantages of its own.
Because contruction is split across multiple calls, a JavaBean may be in an insconsistent state partway through its construction.

### Luckily, there is a third alternative that combines the safety of telescoping constructor pattern with the readability of the JavaBeans pattern.

It is a form of the `Builder` pattern.
Alternative way to construct complex objects.
Instead of making a desired object direclty, the client calls the constructor(or a static factory) with all the required 
parameters and gets a `builder object`. Then the client calls setter-like methods on the builder object to set each optional parameter of interest. Finally, the client calls a parameterless `build` method to generate the object, which is immutable. 

Builder pattern aims to “Separate the construction of a complex object from its representation so that the same construction process can create different representations.”

The builder is a static member class of the class it builds.

Example:

```
public class NutritionFacts{
  //parameters initialized to default values(if any)
  private int servingSize;
  private int servings;
  private int calories;
  private int fat;
  private int sodium;
  private int carbohydrates;

  public static class Builder{
  //required parameters
  private final int servingSize;
  private final int servings;
  
  //optional parameters
  private int calories = 0;
  private int fat = 0;
  private int carbohydrates = 0;
  private int sodium = 0;
  
  public Builder(int servingSize, int servings){
  this.servingSize = servingSize;
  this.servings = servings;
  }
  
  public Builder calories(int val){
  calories = val;
  return this;
  }

  public Builder fat(int val){
  fat = val;
  return this;
  }
  
  public Builder sodium(int val){
  sodium = val;
  return this;
  }
  
  public NutritionFacts(){
  return new NutritionFacts(this);
  }
}
```

Note that `NutritionFacts` is immuatable, and that all parameter default values are in a single location.

The builder's setter methods return the builder itself so that invocations can be chained. 

Here is how the client code looks:

```
NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8).
    calories(100).sodium(35).carbohydrates(27).build();
```

that above created user object does not have any setter method, so it’s state can not be changed once it has been built. This provides the desired immutability.

# Item 3
### Enforce The Singleton property with a private constructor or an enum type

A `Singleton` is simply a class that is instantiated exactly once, can have only one object (an instance of the class) at a time.

To design a singleton class:

1) Make constructor as private.

2) Write a static method that has return type object of this singleton class. Here, the concept of Lazy initialization is 
used to write this static method.

Example:
```
//Singelton with public final field
public class Elvis{
  public static final Elvis INSTANCE = new Elvis();
  
  private Elvis(){....}
  
  public void leaveTheBuilding(){....}
 }
```
Could also be done as follows:
```
// Java program implementing Singleton class 
// with getInstance() method 
class Singleton 
{ 
    // static variable single_instance of type Singleton 
    private static Singleton single_instance = null; 
  
    // variable of type String 
    public String s; 
  
    // private constructor restricted to this class itself 
    private Singleton() 
    { 
        s = "Hello I am a string part of Singleton class"; 
    } 
  
    // static method to create instance of Singleton class 
    public static Singleton getInstance() 
    { 
        if (single_instance == null) 
            single_instance = new Singleton(); 
  
        return single_instance; 
    } 
} 
```
Here is what the driver class looks like:

```
// Driver Class 
class Main 
{ 
    public static void main(String args[]) 
    { 
        // instantiating Singleton class with variable x 
        Singleton x = Singleton.getInstance(); 
  
        // instantiating Singleton class with variable y 
        Singleton y = Singleton.getInstance(); 
  
        // instantiating Singleton class with variable z 
        Singleton z = Singleton.getInstance(); 
  
        // changing variable of instance x 
        x.s = (x.s).toUpperCase(); 
  
        System.out.println("String from x is " + x.s); 
        System.out.println("String from y is " + y.s); 
        System.out.println("String from z is " + z.s); 
        System.out.println("\n"); 
  
        // changing variable of instance z 
        z.s = (z.s).toLowerCase(); 
  
        System.out.println("String from x is " + x.s); 
        System.out.println("String from y is " + y.s); 
        System.out.println("String from z is " + z.s); 
    } 
} 
```

Output:
```
String from x is HELLO I AM A STRING PART OF SINGLETON CLASS
String from y is HELLO I AM A STRING PART OF SINGLETON CLASS
String from z is HELLO I AM A STRING PART OF SINGLETON CLASS


String from x is hello i am a string part of singleton class
String from y is hello i am a string part of singleton class
String from z is hello i am a string part of singleton class
```
![1](https://github.com/RamziJabali/Effective-Java-Practices/blob/master/pics-for%3Deffective-java/singleton-class-java.png)

Explanation:
1) In the Singleton class, when we first time call getInstance() method, it creates an object of the class with name single_instance and return it to the variable. 

2) Since single_instance is static, it is changed from null to some object. 

3) Next time, if we try to call getInstance() method, since single_instance is not null, it is returned to the variable, instead of instantiating the Singleton class again. This part is done by if condition.

### Implementing Singleton class with method name as that of class name
```
   // static method to create instance of Singleton class 
    public static Singleton Singleton() 
    { 
        // To ensure only one instance is created 
        if (single_instance == null) 
        { 
            single_instance = new Singleton(); 
        } 
        return single_instance; 
    } 
```
