class Store {
    #profile = {}
    #userList = []
    #changeUserListListener = function () {}
    #changeProfileListener = function () {}

    getProfile() {
        return this.#profile;
    }

    setProfile(value) {
        this.#profile = value;
        this.#changeProfileListener(value)
    }

    getUserList() {
        return this.#userList;
    }

    setUserList(value) {
        this.#userList = value;
        this.#changeUserListListener(value)
    }

    setChangeUserListListener(value) {
        this.#changeUserListListener = value
    }

    setChangeProfileListener(value) {
        this.#changeProfileListener = value
    }
}

export default Store;