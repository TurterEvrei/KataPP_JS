import {User} from "./user.js";

class ApiService {

    static async fetchProfile() {
        let result = null;
        await $.getJSON(
            'api/user',
            {},
            (data) => result = data
        );
        return result;
    }

    static async fetchUsers() {
        let result = []
        await $.getJSON(
            'api/admin',
            {},
            (data) => result = data
        )
        return result;
    }

    static async createUser(user) {
        let result = null
        await $.ajax(
            'api/admin',
            {
                method: 'post',
                contentType: 'application/json',
                data: JSON.stringify(user),
                success: (res) => result = res
            }
        )
        return result;
    }

    static async editUser(user) {
        let result = false
        await $.ajax({
                url: 'api/admin',
                method: 'put',
                contentType: 'application/json',
                data: JSON.stringify(user),
                success: (res) => result = res
            }
        )
        return result;
    }

    static async deleteUser(id) {
        let result = false
        await $.ajax(
            `api/admin/${id}`,
            {
                method: 'delete',
                success: (data) => result = data
            }
        )
        return result;
    }

}

export {ApiService};