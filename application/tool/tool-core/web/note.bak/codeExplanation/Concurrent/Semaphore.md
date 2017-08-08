```
// http://blog.csdn.net/lipeng_bigdata/article/details/52165426
        // 创建一个计数阈值为5的信号量对象, 只能5个线程同时访问
        // 默认的是非公平性的, 如果想要公平, 使用 new Semaphore(5, true);
        Semaphore semp = new Semaphore(5);
        try {
            // 申请许可
            /**
             *   Semaphore也提供了boolean tryAcquire(long timeout, TimeUnit unit)、tryAcquire()等限制时间内阻塞或非阻塞实现方式，
             *   比较简单，但是有一点，公平模式下的tryAcquire()、tryAcquire(int permits)会打破原先的公平性，因为其是通过调用sync的nonfairTryAcquireShared()方法的方式实现的，
             *   需要另外使用tryAcquire(long timeout, TimeUnit unit)、tryAcquire(int permits, long timeout, TimeUnit unit)来保持公平性。
             *
             */
            semp.acquire();
            try {
                handleRequest(request, response);
                return response;
            } catch (Exception e) {
                e.printStackTrace();
                ResponseBuilder.addResult(response, new ExternalException("server exception").getMessage());
                return response;
            } finally {
                /**
                 * 放一个许可，将其返回给信号量。释放一个许可，将可用的许可数增加 1。如果任意线程试图获取许可，则选中一个线程并将刚刚释放的许可给予它。
                 * 然后针对线程安排目的启用（或再启用）该线程。
                 * 不要求释放许可的线程必须通过调用 acquire() 来获取许可。通过应用程序中的编程约定来建立信号量的正确用法。
                 */
                semp.release();
            }
```