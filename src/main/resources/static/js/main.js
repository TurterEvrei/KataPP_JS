import {ApiService} from "./service.js";
import Store from "./store.js";
import {ROLES, User, Role} from "./user.js";

const MODAL_TYPES = {
    EDIT: 'edit',
    DELETE: 'delete',
}

const store = new Store();
store.setChangeUserListListener((userList) => renderUsersTableData(userList))
store.setChangeProfileListener((prof) => {
    renderHeader(prof)
    renderProfileTableData(prof)
    console.log(prof)
})

initRenders()

function initRenders() {
    ApiService.fetchProfile().then((res) => {
        store.setProfile(res)
        tabsInitClasses()
    })
    ApiService.fetchUsers().then((res) => store.setUserList(res))

    renderNewUserForm()
}

function inputUserFieldsHandler(input, user) {
    user[input.name] = input.name === 'id' || input.name === 'age' ? Number(input.value) : input.value
}

function renderHeader(user) {
    $('#header').html(
        `<div class="fw-bold p-1 text-white">
        ${user.email}
       </div>
        <div class="text-light p-1"> 
        with roles: ${user.roles.map(role => role.roleType).join(', ')}
        </div>`
    )
}

function renderProfileTableData(user) {
    $('#profileTableBody').html(
        `<tr>
        <td>${user.id}</td>
        <td>${user.lastName}</td>
        <td>${user.age}</td>
        <td>${user.email}</td>
        <td>${user.roles.map(role => role.roleType).join(', ')}</td>
        </tr>`
    )
}

function renderUsersTableData(users) {
    let content = ``
    const tableBody = $('#usersTableBody')

    users.forEach((user) => {
        content += `<tr>
            <td>${user.id}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.roles.map(role => role.roleType).join(', ')}</td>
            <td>
                <button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target='#modalUser' 
                data-userId=${user.id} data-target=${MODAL_TYPES.EDIT}>
                    Edit
                </button>
            </td>
            <td>
                <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target='#modalUser' 
                data-userId=${user.id} data-target=${MODAL_TYPES.DELETE}>
                    Delete
                </button>
            </td>
            </tr>`
    })

    tableBody.html(content)
    tableBody.find('button').on('click', (event) => {
        const user = users.find((user) => user.id === Number($(event.target).attr('data-userId')))
        const type = $(event.target).attr('data-target')

        renderModal(user, type)
    })
}

function renderModal(user, type) {
    const modal = $('#modalUser')
    const inputs = modal.find('input')
    const select = modal.find('select')
    const title = $('#modalTitle')
    const submitBtn = $('#submitBtn')
    submitBtn.off('click')

    let rolesHtml = ``
    ROLES.forEach(
        (role) =>
            rolesHtml += `<option value=${role.roleType} ${user.roles.some((r) => r.roleType === role.roleType) ? `selected` : ``}>${role.roleType}</option>`
    )

    $('#modalUserRoles').html(rolesHtml)

    if (type === MODAL_TYPES.DELETE) {
        inputs.attr('disabled', true)
        select.attr('disabled', true)
        title.html('Delete user')
        submitBtn
            .removeClass('btn-primary')
            .addClass('btn-danger')
            .html('Delete')
            .on('click', (event) => {
                event.preventDefault()
                ApiService.deleteUser(user.id).then((res) => {
                    if (res) {
                        store.setUserList(store.getUserList().filter((u) => u.id !== user.id))
                        modal.modal('toggle')
                    }
                })
            })
    } else {
        inputs.attr('disabled', false)
        select.attr('disabled', false)
        title.html('Edit user')
        submitBtn
            .removeClass('btn-danger')
            .addClass('btn-primary')
            .html('Edit')
            .on('click', (event) => {
                event.preventDefault()
                const editedUser = new User()
                const arrInput = inputs.toArray()
                arrInput.forEach((input) => inputUserFieldsHandler(input, editedUser))
                editedUser['roles'] = select
                    .val()
                    .map(type => user.roles.filter(r => r.roleType === type)[0] || new Role(type))
                ApiService.editUser(editedUser).then((res) => {
                    if (res) {
                        store.setUserList(store.getUserList().map((u) => u.id === editedUser.id ? editedUser : u))
                        if (editedUser.id === store.getProfile().id) {
                            store.setProfile(editedUser)
                        }
                        modal.modal('toggle')
                    }
                })
            })
    }

    $('#modalUserId').val(user.id).attr('disabled', true)
    $('#modalUserAge').val(user.age)
    $('#modalUserName').val(user.lastName)
    $('#modalUserEmail').val(user.email)
}

function renderNewUserForm() {
    let rolesHtml = ``
    ROLES.forEach((role) => rolesHtml += `<option value="${role.roleType}">${role.roleType}</option>`)
    $('#addUserRoles').html(rolesHtml)

    $('#newUserBtn').on(
        'click',
        (event) => {
            event.preventDefault()
            const newUser = new User
            const form = $('#newUserForm')
            form
                .find('input').toArray()
                .forEach((input) => inputUserFieldsHandler(input, newUser))
            newUser.roles = form
                .find('select')
                .val()
                .map(type => new Role(type))
            ApiService.createUser(newUser).then((res) => {
                if (res) {
                    store.setUserList([...store.getUserList(), res])
                    $('#nav-usersTable-tab').addClass('active')
                    $('#nav-usersTable').addClass('show active')
                    $('#nav-newUser-tab').removeClass('active')
                    $('#nav-newUser').removeClass('show active')
                }
            })
        }
    )
}

function tabsInitClasses() {
    if (store.getProfile().roles.some((role) => role.roleType === 'ADMIN')) {
        $('#v-pills-admin-tab')
            .addClass('active')
            .attr('aria-selected', 'true')
        $('#v-pills-admin').addClass('show active')
    } else {
        $('#v-pills-admin-tab').hide()
        $('#v-pills-admin').hide()
        $('#v-pills-user-tab')
            .addClass('active')
            .attr('aria-selected', 'true')
        $('#v-pills-user').addClass('show active')
    }
}