// Launch the pool with four threads.
boost::asio::thread_pool pool(4);

// Submit a function to the pool.
boost::asio::post(pool, my_task);

// Submit a lambda object to the pool.
boost::asio::post(pool,
    []()
    {
      ...
    });

// Wait for all tasks in the pool to complete.
pool.join();