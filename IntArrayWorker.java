public class IntArrayWorker
{
  /** two dimensional matrix */
  private int[][] matrix = null;

  /** set the matrix to the passed one
    * @param theMatrix the one to use
    */
  public void setMatrix(int[][] theMatrix)
  {
    matrix = theMatrix;
  }

  /**
   * Method to return the total
   * @return the total of the values in the array
   */
  public int getTotal()
  {
    int total = 0;
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < matrix[0].length; col++)
      {
        total = total + matrix[row][col];
      }
    }
    return total;
  }

  /**
   * Method to return the total using a nested for-each loop
   * @return the total of the values in the array
   */
  public int getTotalNested()
  {
    int total = 0;
    for (int[] rowArray : matrix)
    {
      for (int item : rowArray)
      {
        total = total + item;
      }
    }
    return total;
  }

  /**
   * Method to fill with an increasing count
   */
  public void fillCount()
  {
    int numCols = matrix[0].length;
    int count = 1;
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < numCols; col++)
      {
        matrix[row][col] = count;
        count++;
      }
    }
  }

  /**
   * print the values in the array in rows and columns
   */
  public void print()
  {
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < matrix[0].length; col++)
      {
        System.out.print( matrix[row][col] + " " );
      }
      System.out.println();
    }
    System.out.println();
  }


  /**
   * fill the array with a pattern
   */
  public void fillPattern1()
  {
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < matrix[0].length;
           col++)
      {
        if (row < col)
          matrix[row][col] = 1;
        else if (row == col)
          matrix[row][col] = 2;
        else
          matrix[row][col] = 3;
      }
    }
  }
	/*
   * @param num 	the value to find ini the matrix
   * @return 	The number of times num is found
   */
   public int getCount(int nums) {
	   int result = 0;
	   for (int[] row : matrix){
		for (int val : row)
			if (val == nums) result++;

	}
		return result;
	}

	public int getLargest(){
		int largest = matrix[0][0];
	   for (int[] row : matrix){
		for (int item : row)
			if (item > largest)
				largest = item;

	   }
		return largest;
	}

	public int getColTotal(int column){
		int total = 0;
		for(int i =0; i<matrix.length;i++){
			total += matrix[i][column];
		}
		return total;
	}

	public void reverseRows()
  {
		for(int i = 0; i < matrix.length; i++)
    {
      for(int j = 0; j < matrix[i].length/2; j++)
      {
        int temp = matrix[i][j];
        matrix[i][j] = matrix[i][matrix[i].length - j - 1];
        matrix[i][matrix[i].length - j - 1] = temp;
      }
		}
	}

	//out of everything, we only modify picture
	//although we should know how to use the other things
}
