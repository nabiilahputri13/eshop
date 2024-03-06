# Reflection - Tutorial 4
1. This TDD flow is definitely useful. It enhanced the code independence, it refactors for clarity, it optimizes execution tiem, adn improve failure diagnostic
2. 

# Reflection - Tutorial 3
1. I applied
   - OCP : I added 'update' method to Car and Product (model) so that if subclasses are made, updates could still be done like what the subclasses desired and not stuck by the name, quantity, and color
   - SRP & DIP : I divided CarController and ProductController into different file
   - LSP : I removed 'extends ProductController' from CarController so that it doesn't inherit from another class
   - ISP : CarService and ProductService are located in a different file because at one time, only one of those services are used.
  
2. - Single Responsibility Principle (SRP): Each class should have only one reason to change, making the code easier to understand, maintain, and extend. This leads to more modular and cohesive code.
   - Open/Closed Principle (OCP): Software entities should be open for extension but closed for modification. This encourages the use of abstraction and polymorphism, allowing new functionality to be added without altering existing code. (e.g. splitting  CarController and ProductController)
   - Liskov Substitution Principle (LSP): Objects of a superclass should be replaceable with objects of its subclasses without affecting the correctness of the program. This promotes code reuse and ensures that inheritance hierarchies are designed correctly. (e.g class CarController shouldn't depends on ProductController)
   - Interface Segregation Principle (ISP): Clients should not be forced to depend on interfaces they do not use. By segregating interfaces into smaller and more specific ones, this principle prevents the creation of overly large and monolithic interfaces, leading to better code maintainability and flexibility. (e.g. locating CarService and ProductService in a different file)
   - Dependency Inversion Principle (DIP): High-level modules should not depend on low-level modules. Both should depend on abstractions. This promotes loose coupling between classes and modules, making the codebase more flexible, scalable, and easier to test and maintain. 

3. - Hard to Change: Without SOLID principles, making changes to the code becomes tough. It's like trying to fix one thing, but accidentally breaking something else. (e.g. if class CarController depends on ProductController)
   - Tough to Understand: When code is messy and tangled up, it's like trying to read a messy handwriting. It's hard to figure out what each part does. 
   - Hard to Test : If the code isn't organized well, it's hard to make sure everything works properly.
   - Hard to Add Code : If we want to write a new code but haven't applied SOLID principle, we should make many changes to the existing code so that it works properly.

# Reflection - Tutorial 2
1. When I ran the code coverage html, it turns out that my tests for repository, service, and controller part aren't doing really good. So I fixed these part by adding more tests. The tests I added are actually quite similar to the previous one, but I just added stuffs like update & delete for product with valid, invalid, and null ID.
2. Yes, I think my current implementation has met the definition of Continuous Integration and Continuous Deployment. A  CI/CD implementation can be considered effective if it involves frequent integration (A key aspect of CI/CD is the frequent integration of code changes into the main repository and the automated deployment of these changes to production), comprehensive automated testing ( Continuous Integration emphasizes automated testing to ensure that code changes don't introduce defects), and automated deployment to production ( Continuous Deployment involves automatically deploying code changes to production after successful testing in the CI pipeline). These elements collectively support the goals of delivering high-quality software continuously and efficiently.
   
# Reflection 1 - Tutorial 1
Clean code principles applied:
- Descriptive naming
The class and method names are clear and describe their purpose well. For example, ProductController, ProductService, ProductRepository, etc.

- Separation of concerns
The code follows the MVC (Model-View-Controller) architecture, separating the concerns of handling HTTP requests, business logic, and data persistence.

- Dependency injection
The use of Spring's @Autowired annotation promotes loose coupling by injecting dependencies into beans rather than hard-coding them.

- Code reusability
The ProductServiceImpl class utilizes the ProductRepository interface for data access, promoting code reuse.

- Single responsibility principle
Each class/method has a single responsibility. For instance, the ProductService interface and its implementation handle product-related operations only.

However, I think that secure coding practices have not been applied to the code.
Here are some possible improvements:
- Input validation
Implement input validation to ensure that user inputs are properly validated before processing. (e.g. Some dialogs like 'Are you sure you want to add this product?')

- Error handling
Enhance error handling by adding appropriate try-catch blocks or exception handling mechanisms to handle unexpected errors gracefully.

- Session management
Secure session management involves securely handling user sessions, including unique IDs, HTTPS transmission, proper expiration, and protection against attacks like session fixation and CSRF.

# Reflection 2 - Tutorial 2
1. For me, writing the unit test is kind of challenging since I have never really understand what is the purpose of writing it before. But after joining Advanced Programming class, I realized that tests are actually something necessary when we are going to build an application. By writing the test, we could know if our code has bugs or errors. However, I think the amount of unit tests needed to verify a program depends on the program itself, if the program only has few simple features then probably fewer unit tests are needed compared to a complex program. To ensure that our unit tests are enough to verify our program, code coverage can be a useful metric. Code coverage measures the percentage of the code that is executed by the tests. However, achieving 100% code coverage does not guarantee that our code is free of bugs or errors. It only indicates that every line of code has been executed at least once during testing.
2.  I think if the new functional test suite follows a similar structure as the existing one it could led to some potential issues, such as:
- Code duplication
Reason : Repeating setup procedures and instance variables in each test suite can lead to maintenance issues and increased code complexity.
Improvement : Consider refactoring common setup procedures and instance variables into reusable methods or helper classes. This reduces duplication and promotes code reusability.

- Readability and maintainability
Reason : Highly similar codes between  test suites can make it hard to distinguish and understand their purposes.
Improvement : Clearly document each test suite's purpose and use descriptive names for classes, methods, and variables.

- Test dependencies
Reason :  Dependencies can make tests brittle and increase the likelihood of false positives or false negatives.
Improvement : Ensure that each test case is independent and does not rely on the state or outcome of other tests. 

- Test isolation 
Reason : If the new test suite mixes different concerns or functionalities, it could result in unclear test cases and failure diagnosis.
Improvement : Ensure that each test suite focuses on a single aspect of the application's functionality.

