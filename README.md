# Producer Consumer Problem (Java)

# # # This is my implementation of the Producer Consumer Problem. 

It could be made more efficient by introducing semaphores, but to solve the issue at hand
I used scheduled threads instead.
In my abstraction, the product being produced is a burger.
The burger must first be cooked, before it is ready to be added to the buffer by the Producer.
The buffer has a maximum size of 5.
The Consumer waits until the buffer has at least 1 burger, then takes the burger and 
delivers it to a customer. 

Once the burger has been cooked and delivered, "Burger[i] has been delivered!" will 
appear on the console.

--

I decided to use two threads and 5 tasks to complete the problem. 
# # # The tasks are as follows:

1. Cook the burger 
2. Place the burger into the buffer (queue)
3. Extract the burger from the buffer 
4. Deliver the burger to the customer 
5. Check to see if the burger has been cooked/delivered. 

-- 

# # # I synchronized the shared variables by...
1. using the volatile keyword when defining the buffer
2. Making the buffer a BlockingQueue
3. Using AtomicBooleans for the burger states (cooked/delivered/inBuffer)
4. Using SynchronizedLists to store the Orders and the burgers that have been extracted from the buffer.
