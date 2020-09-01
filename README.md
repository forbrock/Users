# Users
CRUD implementation
How to use methods:
- findUser(String query) takes sql query like SELECT * FROM &lt;table&gt;, returns the result to the list 'users' in UserRepository class;
- addUser(User user) takes a user object and adds it to the database;
- updateUser(int id, User newUser), updates user object by id in the database;
- removeUser(int id) should to remove user from the database by id (not yet implemented).
