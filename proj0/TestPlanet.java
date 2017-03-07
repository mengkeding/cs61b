/**write a test that creates two planets and prints out the pairwise force between them*/

public class TestPlanet{

	public static void main(String[] args){
      Planet Samh = new Planet (1.0, 0.0, 3.0, 4.0, 10.0, "jupiter.gif");
      Planet AEgir = new Planet (3.0, 3.0, 6.0, 8.0, 5.0, "jupiter.gif");
      System.out.println(Samh.calcForceExertedBy(AEgir));
      System.out.println(Samh.calcForceExertedByX(AEgir));
      System.out.println(Samh.calcForceExertedByY(AEgir));
      System.out.println(AEgir.calcForceExertedBy(Samh));
      System.out.println(AEgir.calcForceExertedByX(Samh));
      System.out.println(AEgir.calcForceExertedByY(Samh));
	}
}





















