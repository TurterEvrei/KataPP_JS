class User {
    id;
    lastName;
    age;
    email;
    password;
    roles;
}

class Role {
    id;
    roleType;
    // user;

    constructor(roleType) {
        this.roleType = roleType
    }
    // constructor(roleType, userId) {
    //     this.roleType = roleType
    //     // this.user = userId
    // }
}

const ROLES = [
    {
        id: null,
        roleType: 'ADMIN'
    }, {
        id: null,
        roleType: 'USER'
    }
]

// const ROLES = [
//     'ADMIN',
//     'USER'
// ]

export {User, ROLES, Role}