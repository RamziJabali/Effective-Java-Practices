public class CoordinatesStaticFactoryMethod {
    private double x;
    private double y;

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

    @Override
    public String toString() {
        return "X: " + x
                + "Y: " + y;
    }
}
