# Table of Contents  
- [Item 1:](#item-1)
  - [1. Creating & Destroying Objects](#1-creating--destroying-objects)
  - [2. ](#2-)


# Item 1:
# 1. Creating & Destroying Objects

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
Because contruction is split across multiple calls, a JavaBean may be insconsistent stat partway through its construction.

### Luckily, there is a third alternative that combines the safety of telescoping constructor pattern with the readability
### of the JavaBeans pattern.

It is a form of the `Builder` pattern.
Instead of making a desired object direclty, the client calls the constructor(or a static factory) with all the required 
parameters and gets a `builder object`. Then the client calls setter-like methods on the builder object to set each ptional parameter of interest. Finally, the client calls a parameterless `build` method to generate the object, which is immutable. 

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
