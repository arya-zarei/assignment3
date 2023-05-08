public class FindShortestPath //compute a shortest path from the initial chamber to the exit
{
	public static void main (String[] args)
	{
		try
		{
			if (args.length < 1) throw new Exception("No input file specified"); //if nothing is inputed
			{
				DLPriorityQueue<Hexagon> warrior = new DLPriorityQueue<>(); //warrior priority queue
				String dungeonFileName = args[0]; //file name string
				Dungeon dungeon = new Dungeon(dungeonFileName); //calls file name from run configurations
				Hexagon start = dungeon.getStart(); //initial spot/start
				
				start.markEnqueued(); // start is marked as enqueued
				warrior.add(start, 0); //warrior is at start priority zero
				
				while (!warrior.isEmpty()) //while warrior queue is not empty
				{ 
					Hexagon current = warrior.removeMin(); //current chamber is the start (lowest priority)
					current.markDequeued(); //current chamber is dequeued
					
					if (current.isExit()) //if current is at the exit, reached the finished, exit the loop
					{
						break;
					}
					
					if (current.isDragon() || current.isWall()) /**if current is a wall or dragon chamber, warrior queue and current goes
						to start as it tries a different path **/
					{
						current = start;
						warrior.add(start, 0);
					}
					
					for (int i = 0; i < 6; i++) //checks all 6 sides of hexagon adjacent chambers
					{
						if( (current.getNeighbour(i) != null) && (current.getNeighbour(i).isDragon()) ) 			
						{ //if neighbour/adjacent is not null and neighbour is a dragon chamber
							current = start; //resets chamber and warrior to start as this path is not eligible 
							warrior.add(start, 0);
						}
					}

					for (int i = 0; i < 6; i++) // for loop for 6 sides of hexagon to check for the next chamber to move to
					{
					    Hexagon neighbour = current.getNeighbour(i); //neighbour is the current neighbour (goes through one at a time from 1-6)

					    if (neighbour != null && !neighbour.isWall() && !neighbour.isMarkedDequeued())
					    {//if neighbour is available and neighbour is not a wall or already dequeued
					        int D = 1 + current.getDistanceToStart(); //int D is 1 + the distance from the starting chamber
					        
					        if (neighbour.getDistanceToStart() > D) //if neighbours distance to start is greater than D/neighbour is ahead of D
					        {
					            neighbour.setDistanceToStart(D); //sets neighbour distance starting chamber as D
					            neighbour.setPredecessor(current); //sets current as the predecessor of neighbour (for shortest path)
					            neighbour.markEnqueued(); //neighbour has now been marked as enqueued
					            warrior.add(neighbour, D); //adds neighbor to warrior queue at the placement/priority of D
					            
					            if (neighbour.isMarkedEnqueued() && neighbour.getDistanceToStart() != D) 
					            { /**checks if neighbour has been marked as enqueued and if D is no longer equal to distance from 
					            	neighbour to start (has been modified) **/
					                warrior.updatePriority(neighbour, D + neighbour.getDistanceToExit(dungeon));
					                /** if so warrior updates its priority at neighbour with the distance from start to neighbour plus neighbour to
					                exit which is the entire route**/
					            }
					        }
					        
					        else if (!neighbour.isMarkedEnqueued()) //otherwise if neighbour is not marked as enqueued
					        {   //changes priority to distance from start to neighbour to exit (entire distance)
					        	double priority = neighbour.getDistanceToStart() + neighbour.getDistanceToExit(dungeon);
					        	warrior.add(neighbour, priority); //this priority is then added to warrior
					        }
					        neighbour.markEnqueued(); //lastly neighbour is marked as enqueued
					    }
					}
				}			
			}
		}
 
		catch(Exception e)	//catch exception e (catches all the types of exceptions provided for the assignment	
		{
			System.out.println(e.getMessage()); //prints all the different types of catch messages based on the error occurred
		}
	}
}