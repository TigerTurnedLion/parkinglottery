/*
Design a parking lot using object-oriented principles

Goals:
- Your solution should be in Java - if you would like to use another language, please let the interviewer know.
- Boilerplate is provided. Feel free to change the code as you see fit

Assumptions:
- The parking lot can hold motorcycles, cars and vans
- The parking lot has motorcycle spots, car spots and large spots
- A motorcycle can park in any spot
- A car can park in a single compact spot, or a regular spot
- A van can park, but it will take up 3 regular spots
- These are just a few assumptions. Feel free to ask your interviewer about more assumptions as needed

Here are a few methods that you should be able to run:
- Tell us how many spots are remaining
- Tell us how many total spots are in the parking lot
- Tell us when the parking lot is full
- Tell us when the parking lot is empty
- Tell us when certain spots are full e.g. when all motorcycle spots are taken
- Tell us how many spots vans are taking up

Hey candidate! Welcome to your interview. I'll start off by giving you a Solution class. To run the code at any time, please hit the run button located in the top left corner.
*/

package buzz.rationalagents.parkinglottery;

import java.util.ArrayList;

class Solution {

  public static void mainlot() {

    int bikespots = 20, compactspots = 50, regularspots = 30;
    ParkingLot lot = new ParkingLot( bikespots,compactspots,regularspots );

    System.out.println("Number of spots in the lot: " + Integer.toString(lot.TotalSpots()));

    Motorcycle mc = null;
    for( int i = 0; i < 96 ; i++ ){
      mc = new Motorcycle();
      mc.park(lot);
    }

    Car car = new Car();
    car.park(lot);

    Van van = new Van();
    van.park(lot);

    System.out.println("Number of spots remaining: " + Integer.toString(lot.SpotsRemain()));

    System.out.println("Is parking lot full?  " + (lot.isFull()?"Yes":"No"));
    System.out.println("Is parking lot empty?  " + (lot.isEmpty()?"Yes":"No"));
    System.out.println("Number of spots taken by vans: " + Integer.toString( lot.VanTaken()));
    System.out.println("Number of spots taken by cars: " + Integer.toString( lot.CarTaken()));
    System.out.println("Number of spots taken by motorcycles: " + Integer.toString( lot.BikeTaken()));
    System.out.println("Are all motorcycle spots taken?: " + (lot.motocycleTaken()?"Yes":"No"));
    System.out.println("Are all compact spots taken?: " + (lot.compactTaken()?"Yes":"No"));
    System.out.println("Are all regular spots taken?: " + (lot.regularTaken()?"Yes":"No"));

    System.out.println("");
    System.out.println("Car backout -->");
    System.out.println("");
    car.backout(lot);

    System.out.println("Number of spots remaining: " + Integer.toString(lot.SpotsRemain()));
    System.out.println("Is parking lot full?  " + (lot.isFull()?"Yes":"No"));
    System.out.println("Is parking lot empty?  " + (lot.isEmpty()?"Yes":"No"));
    System.out.println("Number of spots taken by cars: " + Integer.toString( lot.CarTaken()));

    System.out.println("");
    System.out.println("Van backout ==>");
    System.out.println("");
    van.backout(lot);
    System.out.println("Number of spots remaining: " + Integer.toString(lot.SpotsRemain()));
    System.out.println("Number of spots taken by vans: " + Integer.toString( lot.VanTaken()));
  }
}
abstract class Spot{
  Vehicle vehicle = null;
}

class BikeSpot extends Spot{}
class CompactSpot extends Spot{}
class RegularSpot extends Spot{}

interface Vehicle{

  public void park(ParkingLot lot);

  default public void backout(ParkingLot lot){
    ArrayList<Spot> spots = null;
    spots = lot.FindSpots(this);

    for( Spot s : spots ){
      s.vehicle = null;
    }
  };
}

class Motorcycle implements Vehicle{
  public void park(ParkingLot lot){
    Spot s = null;

    s = lot.getBikeSpot();
    if( s != null ){
      s.vehicle = this;
    }
    else{
      System.out.println("No spot for you");
    }
  }
}
class Car implements Vehicle{
  public void park(ParkingLot lot){
    Spot s = null;

    s = lot.getCarSpot();
    if( s != null ){
      s.vehicle = this;
    }
    else{
      System.out.println("No spot for you 2 boo");
    }
  }
}
class Van implements Vehicle{
  public void park(ParkingLot lot){

    ArrayList<Spot> spots;

    spots = lot.getVanSpots();
    //finish this
    if( spots != null ){
      for(Spot s : spots){
        s.vehicle = this;
      }
    }
    else{
      System.out.println("No shoes for yo vans");
    }
  }
}

class ParkingLot{

  //use an array for collection of static spots.  
  //ArrayList nogood since parking lots do not dynamically add / remove spots

  Spot[] Spots = null;

  ParkingLot(){ 
  }

  ParkingLot(int bike,int compact,int regular){
    int total = 0, i, j, k;
    total += bike + compact + regular;

    Spots = new Spot[total];

    //bike spots
    for( i = 0; i < bike; i++){
      Spots[i] = new BikeSpot();
    }

    //compact spots
    for( j = i; j < bike + compact; j++ ){ 
      Spots[j] = new CompactSpot();
    }

    //regular spots
    for( k = j; k < total; k++ ){ 
      Spots[k] = new RegularSpot();
    }
  }
  Spot getBikeSpot(){

    Spot spot = null;

    for( int i = 0; i < Spots.length; i++ ){
      if( Spots[i].vehicle == null ){
        spot = Spots[i];
        break;
      }
    }
    return spot;
  }
  Spot getCarSpot(){
    Spot spot = null;

    for( int i = 0; i < Spots.length; i++ ){
      if(( Spots[i] instanceof CompactSpot || Spots[i] instanceof RegularSpot ) && Spots[i].vehicle == null ){
        spot = Spots[i];
        break;
      }
    }

    return spot;
  }
  ArrayList<Spot> getVanSpots(){

    ArrayList<Spot> spots = new ArrayList<>();

    for( int i = 0; i < this.Spots.length - 2 ; i++ ){

      if( Spots[i] instanceof RegularSpot && Spots[i].vehicle == null && 
          Spots[i + 1] instanceof RegularSpot && Spots[i + 1].vehicle == null &&
          Spots[i + 2] instanceof RegularSpot && Spots[i + 2].vehicle == null ){

          spots.add(Spots[i]);
          spots.add(Spots[i + 1]);
          spots.add(Spots[i + 2]);

          break; //add van 1 time
      }
    }

    return spots;
  }
  int TotalSpots(){
    return this.Spots.length;
  }
  int SpotsRemain(){ //O(n)
    int spots = 0;
    for( int i = 0; i < this.Spots.length; i++){
      if( this.Spots[i].vehicle == null ){
        spots++;
      }
    } 
    return spots;
  }

  boolean isFull(){

    for( int i = 0; i < Spots.length ; i++){
      if( Spots[i].vehicle == null ){
        return false;
      }
    }
    return true;
  }
  boolean isEmpty(){
    for( int i = 0; i < Spots.length ; i++){
      if( Spots[i].vehicle != null ){
        return false;
      }
    }
    return true;
  }
  int VanTaken(){
    int vanspots = 0;

    for( Spot spot : Spots ){
      if( spot.vehicle != null && spot.vehicle instanceof Van ){
        vanspots++;
      }
    }

    return vanspots;
  }
  int CarTaken(){
    int carspots = 0;

    for( Spot spot : Spots ){
      if( spot.vehicle != null && spot.vehicle instanceof Car ){
        carspots++;
      }
    }

    return carspots;
  }
  int BikeTaken(){
    int bikespots = 0;

    for( Spot spot : Spots ){
      if( spot.vehicle != null && spot.vehicle instanceof Motorcycle ){
        bikespots++;
      }
    }

    return bikespots;
  }
  boolean motocycleTaken(){
    for( Spot spot : Spots ){
      if( spot instanceof BikeSpot && spot.vehicle == null ){
        return false;
      }
    }
    return true;
  }
  boolean compactTaken(){
      for( Spot spot : Spots ){
      if( spot instanceof CompactSpot && spot.vehicle == null ){
        return false;
      }
    }
    return true;
  }
  boolean regularTaken(){
      for( Spot spot : Spots ){
      if( spot instanceof RegularSpot && spot.vehicle == null ){
        return false;
      }
    }
    return true;
  }
  ArrayList<Spot> FindSpots(Vehicle v){ // works on all vehicles 

    ArrayList<Spot> spots = new ArrayList<>();

    for( Spot s : Spots ){
      if( s.vehicle != null && s.vehicle == v ){
        spots.add(s);
      }
    }
    return spots;
  }
}
