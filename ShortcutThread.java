public class ShortcutThread implements Runnable{

    //public float[][] shortcuts_output;
    public float[][] shortcuts;
    private float[][] matrix;
    private int size;
    private int start_i;
    private int iterations_i;

    public ShortcutThread(float[][] shortcuts, float[][] matrix, int id, int num_threads){

        //this.shortcuts_output = shortcuts_output;
        this.shortcuts = shortcuts;
        this.matrix = matrix;
        this.size = matrix.length;
        this.iterations_i = size/num_threads;
        this.start_i = id*iterations_i;
        if(id == num_threads - 1){
            this.iterations_i = size - start_i;
        }

    }

    public void run(){

        float[][] these_shortcuts = new float[iterations_i][size];

        for(int j = 0; j < size; j++){

            float[] linear_kj = new float[size];
            for(int i = 0; i < size; i++){
                linear_kj[i] = matrix[i][j];
            }

            for (int i = start_i; i < start_i + iterations_i; i++){
                float min_k = matrix[i][0] + linear_kj[0];
                for(int k = 1; k < size; k++){
                    float shortcut = matrix[i][k] + linear_kj[k];
                    if(shortcut < min_k){
                        min_k = shortcut;
                    }
                }
                these_shortcuts[i-start_i][j] = min_k;
            }

        }

        //shortcuts_output = these_shortcuts;
        for (int i = start_i; i < start_i + iterations_i; i++){
            shortcuts[i] = these_shortcuts[i-start_i];
        }
    }
}