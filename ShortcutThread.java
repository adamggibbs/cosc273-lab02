public class ShortcutThread implements Runnable{

    private float[][] shortcuts;
    final private float[][] matrix;
    final private int size;
    final private int start_i;
    final private int iterations_i;

    public ShortcutThread(float[][] shortcuts, float[][] matrix, int id, int num_threads){

        this.shortcuts = shortcuts;
        this.matrix = matrix;
        this.size = matrix.length;
        if(id == num_threads - 1){
            this.start_i = id*(size/num_threads);
            this.iterations_i = size - start_i;
        } else {
            this.iterations_i = size/num_threads;
            this.start_i = id*iterations_i;
        }

    }

    public void run(){

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
                shortcuts[i][j] = min_k;
            }

        }

    }
}