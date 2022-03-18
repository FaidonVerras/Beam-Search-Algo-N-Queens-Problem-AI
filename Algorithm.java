import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;

public class Algorithm
{
    private HashSet<BoardState> closedSet;
    private ArrayList<BoardState> frontier;
    
    public Algorithm(){
        this.closedSet = new HashSet<BoardState>();
        this.frontier = new ArrayList<BoardState>();
    }
    
    private int getSolutionScore(int N)//This method calculates the expected score of the solution  
    {
        int s = 0;
        for (int i=1; i<=N; i++){ s+=(N-i); }
        return s;
    }
    
    ArrayList<BoardState> BeamSearch(BoardState initialState, int N, int K)
    {

        BoardState currentState = initialState;
        frontier.add(currentState);

        while(true)
        {
            currentState = frontier.remove(0);
            
            ArrayList<BoardState> children = currentState.getChildren();
            
            Collections.sort(children, Collections.reverseOrder());
            ArrayList<BoardState> best_k_children = new ArrayList<BoardState>();

            for (int i=0; i<=K; i++)//This loop finds and collects the K best children based on their score
            {
                best_k_children.add(children.get(i));
            }
            
            for(BoardState state:best_k_children)
            {   //This loop removes all the best children from the best_k_children list that are contained in the closed set.


                if(this.closedSet.contains(state))
                {
                    best_k_children.remove(state);
                }
            }
            frontier.addAll(best_k_children);
            closedSet.add(currentState);
            
            
            for(BoardState state:frontier)
            {   //If any of the frontier states score is equal to the expected solution score,
                //it returns the steps the algorithm made to reach the solution 
                //from the initial state
                if(state.getScore() == getSolutionScore(N))
                {
                    ArrayList<BoardState> solutionPath = new ArrayList<BoardState>();
                    
                    while(state.father != null)
                    {
                        solutionPath.add(state);
                        state = state.father;
                    }
                    Collections.reverse(solutionPath);
                    return solutionPath;
                }
            }
        }
    }   
}