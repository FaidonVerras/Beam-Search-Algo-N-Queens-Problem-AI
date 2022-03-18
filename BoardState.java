import java.util.Random;
import java.util.ArrayList;

public class BoardState implements Comparable<BoardState>
{
    public int N;
    private int[] placements;
    private int score;

    public BoardState father = null;


    BoardState(int N)//Starting Node
    {
        this.N = N;
        this.placements = new int[N];
        
        for(int i = 0; i < this.placements.length; i++){
            Random r = new Random();
            this.placements[i] = r.nextInt(N)+1;
        }
        this.heuristic();
    }

    BoardState(int[] placements, int N)//Child Node
    {    
        this.N = N;
        this.placements = new int[N];
        for(int i = 0; i < N; i++){
            this.placements[i] = placements[i];
        }
    }
        
    public void printBoard()
    {      
        for (int i = 1; i<=N; i++)
        {
            for (int j = 0; j<N; j++)
            {
                if (this.placements[j] == i) System.out.print("|Q");
                else System.out.print("| ");
            }
            System.out.println("|");
        }
        System.out.println("\n");
    }

    private void setScore(int s){this.score = s;}
    public int getScore(){return this.score;}

    @Override
    public int compareTo(BoardState other)
    {
        return Double.compare(this.score,other.score);
    }

    @Override
    public boolean equals(Object obj)
    {
        BoardState other = (BoardState)obj;
        if(this.placements == other.placements)return true;
        return false;
    }
    
    private void heuristic()
    {
        int nonThreats = 0;
        for (int i=0; i<N ; i++)
        {
            for(int j = i+1; j < N; j++)
            {
                if((this.placements[i] != this.placements[j]) && (Math.abs(i - j) != Math.abs(this.placements[i] - this.placements[j])))
                {
                    nonThreats++;
                }
            }
        }
        this.setScore(nonThreats);
    }

    ArrayList<BoardState> getChildren()
    {
        ArrayList<BoardState> children = new ArrayList<BoardState>();//Current state's children

        for(int i=0; i<N; i++)//collumns
        {
            for(int j=1; j<N; j++)//moves
            {
                if(this.placements[i]+j<=N)
                {
                    int [] newPlacementsDown = new int[N];
                    for (int k=0; k<N; k++){newPlacementsDown[k] = this.placements[k];}//copy current state placemnts
                    newPlacementsDown[i] = newPlacementsDown[i]+j;
                    BoardState childDown = new BoardState(newPlacementsDown, this.N);
                    childDown.heuristic(); 
                    childDown.father = this;
                    children.add(childDown);
                }
                 
                if(this.placements[i]-j>=1)
                {
                    int [] newPlacementsUp = new int[N];
                    for (int k=0; k<N; k++){newPlacementsUp[k] = this.placements[k];}//copy current state placemnts
                    newPlacementsUp[i] = newPlacementsUp[i]-j;
                    BoardState childUp = new BoardState(newPlacementsUp,this.N);
                    childUp.heuristic();
                    childUp.father = this;
                    children.add(childUp);
                }
            }   
        }
        return children;
    }
}