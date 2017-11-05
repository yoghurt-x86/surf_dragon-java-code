public class Main
{
    public static void main(String[] args)
    {
        new DragonViewer();

        DragonCurve dragonCurve = new DragonCurve(9);
        System.out.println(dragonCurve.getCurveString());
        dragonCurve.printVmfEntities();
    }
}