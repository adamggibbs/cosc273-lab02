Adam Gibbs
COSC 273
Lab 02
README File

When optimizing my code I first implemetned cache optimization 
and long with little tweaks to the code. Rather than storing
everything in its own variable I performed operations in a single
line which gave a minimal boost to performance. I believe this was 
because there were less variables to clutter the cache levels. Next
I switched the order of the for loops so that I looped through j on 
the outside and i on the inside since it was the call 'matrix[k][j]'
that was not being performed linearly. So for each iteration of the 
j for loop I created an array linear_kj that stored the values 
'matrix[i][j]' for all values of i in [0,matrix.length). This allowed
for more optimal use of caching when finding min_k during the call
'matrix[i][k] + matrix[k][j]'. It is now 'matrix[i][k] + linear_kj[k]'
which k being the iterating variable. This resulted in performance 
around 13-15 times faster than the baseline implementation. 

Next I added multithreading. I evenly split the i for loop into 
num_threads different groups for num_threads (the number of threads
avaiable from the processor in that computer) threads to complete. Each 
thread would loop over all values of j but only certain values of i. 
This means that each thread is responsible for size/num_threads number 
of rows in the shortcut array. I also had it implemented that n of 
any size could be used, even if not divisible by num_threads. It does 
this by simply tacking on the remainder to the final thread. In most cases
the number of threads is small so even if num_threads-1 extra rows were tacked 
on it wouldn't hurt performance too much. By adding multithreading I got 
improvements of about 50-70 times faster than the baseline for n=2048 with
my laptop which has up to 8 threads available to use. And on remus/romulus 
I tested n=4096 with its 32 threads and got performance boosts between 175 
and 195. Here are some outputs:

Best run, my laptop (8 threads), multithreading and caching optimization
|------|------------------|-------------|------------------|---------|
| size | avg runtime (ms) | improvement | iteration per us | passed? |
|------|------------------|-------------|------------------|---------|
|  128 |               19 |        0.46 |              106 |     yes |
|  256 |               27 |        1.71 |              620 |     yes |
|  512 |               23 |       10.19 |             5830 |     yes |
| 1024 |              137 |       31.71 |             7818 |     yes |
| 1235 |              244 |       39.37 |             7703 |     yes |
| 2048 |             1227 |       71.42 |             6997 |     yes |
|------|------------------|-------------|------------------|---------|  

Best run, remus (32 threads), multithreading and caching optimization
|------|------------------|-------------|------------------|---------|
| size | avg runtime (ms) | improvement | iteration per us | passed? |
|------|------------------|-------------|------------------|---------|
|  128 |              242 |        0.06 |                8 |     yes |
|  256 |               13 |        1.88 |             1246 |     yes |
|  512 |               20 |       25.03 |             6453 |     yes |
| 1024 |              104 |       44.30 |            10272 |     yes |
| 1235 |              164 |       55.26 |            11452 |     yes |
| 2048 |              461 |      107.95 |            18613 |     yes |
| 4096 |             3221 |      195.35 |            21331 |     yes |
|------|------------------|-------------|------------------|---------|

I finally tried little things, like making variables private and setting
min_k to the value of k=0 and then starting the loop at k=1. I also tried 
to make sure variables were created in threads and not shared between threads
with the thought that may increase time it takes to read them. I believe that 
these first couple optimizing made a very slight difference but overall these
did not improve on results much if at all. 
