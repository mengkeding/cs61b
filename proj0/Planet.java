public class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Planet(double xP, double yP, double xV,
              double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet p){
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName= p.imgFileName;
	}

	public double calcDistance(Planet p){
		double dxSqure = Math.pow((p.xxPos - this.xxPos),2);
	    double dySqure = Math.pow((p.yyPos - this.yyPos),2);
	    return Math.sqrt(dxSqure + dySqure);
	}
    
    public double calcForceExertedBy(Planet p){
    	return this.mass * p.mass * 6.67 * Math.pow(10,-11) / Math.pow(this.calcDistance(p),2);
    }


    // calcForceExertedByX(Planet p)  calcForceExertedByY(Planet p) tests error, but works fine with calcNetForceExertedByX()  calcNetForceExertedByY() 
    public double calcForceExertedByX(Planet p){
    	double dx = p.xxPos - this.xxPos;
    	return this.calcForceExertedBy(p)* dx / this.calcDistance(p);
    }

    public double calcForceExertedByY(Planet p){
    	double dy = p.yyPos - this.yyPos;
    	return this.calcForceExertedBy(p) * dy / this.calcDistance(p);
    }

    public double calcNetForceExertedByX(Planet[] allPlanets){
    	double forceX = 0.0;
    	for(int i = 0; i < allPlanets.length; i++) {
    		if (this.equals(allPlanets[i]) != true){
    			
    			forceX += this.calcForceExertedByX(allPlanets[i]);
    		}
    	}
    	return forceX;
    }

    public double calcNetForceExertedByY(Planet[] allPlanets){
    	double forceY = 0.0;
    	for(int i = 0; i < allPlanets.length; i++) {
    		if (this.equals(allPlanets[i]) != true){
    			
    			forceY += this.calcForceExertedByY(allPlanets[i]);
    		}
    	}
    	return forceY;
    }

//update the planet's position and velocity instance variables (this method does not need to return anything).
    public void update(double dt, double fX, double fY) {
      double xxAcc = fX / this.mass;
      double yyAcc = fY / this.mass;
      this.xxVel += dt * xxAcc;
      this.yyVel += dt * yyAcc;
      this.xxPos += dt * xxVel;
      this.yyPos += dt * yyVel;
    }


    
    public void draw(){
        StdDraw.picture(this.xxPos, this.yyPos, this.imgFileName);


    }



}


