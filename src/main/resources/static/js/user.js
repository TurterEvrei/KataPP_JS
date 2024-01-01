class User {
    id;
    lastName;
    age;
    email;
    password;
    roles;
}

const ROLES = [
    'ADMIN',
    'USER'
]

export {User, ROLES}