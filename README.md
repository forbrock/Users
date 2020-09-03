# Users
CRUD implementation
How to use methods:
- _save(userObject)_, takes user object and saves it to the database, the id is adds automatically;
- _find(enumerationOption, criterion)_, takes one of the enum options as the first parameter, these are:
    BY_ID, BY_NAME, BY_BIRTH, BY_EMAIL, and the second is Integer when searching by id or String otherwise.
    Returns the first occurrence of the searched criterion;
- _findAll()_, returns a list of users of the entire table;
- _update(userObject)_, updates user object by id in the database.
    Before updating you need to find the existing userObject in the database, modify it and then update;
- _delete(userObject)_, removes the appropriate user object entry from the database.
    Before removing you need to find the existing userObject in the database.
