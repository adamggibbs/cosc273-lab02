public class ShortcutThread implements Runnable{

    // variables to store matrices and information about which rows to process
    private float[][] shortcuts;
    final private float[][] matrix;
    final private int size;
    final private int start_i;
    final private int iterations_i;

    // contructor
    public ShortcutThread(float[][] shortcuts, float[][] matrix, int id, int num_threads){

        // get shortcut matrix and adjacency matrix
        this.shortcuts = shortcuts;
        this.matrix = matrix;
        // get length of matrix
        this.size = matrix.length;
        // if this is the last thread have it take remaining rows left to process
        // otherwise have thread iterate through size/num_threads rows
        if(id == num_threads - 1){
            this.start_i = id*(size/num_threads);
            this.iterations_i = size - start_i;
        } else {
            this.iterations_i = size/num_threads;
            this.start_i = id*iterations_i;
        }

    }

    // run method
    public void run(){

        // iterate through every column
        for(int j = 0; j < size; j++){

            // linearize the iteration through matrix column
            // this replaces the matrix[k][j] call with linear_kj[k]
            float[] linear_kj = new float[size];
            for(int i = 0; i < size; i++){
                linear_kj[i] = matrix[i][j];
            }

            // iterate thru the rows the thread is assigned
            for (int i = start_i; i < start_i + iterations_i; i++){

                // store the first element as the min
                float min_k = matrix[i][0] + linear_kj[0];
                // check all other possible shortcuts to see if there is another min
                for(int k = 1; k < size; k++){
                    float shortcut = matrix[i][k] + linear_kj[k];
                    if(shortcut < min_k){
                        min_k = shortcut;
                    }
                }
                // store the shrotest path for this element in the shortcut array
                // since each thread is assigned different rows there won't be any
                // issues writing directly to the shared shortcuts array
                shortcuts[i][j] = min_k;
            }

        }

    }
}