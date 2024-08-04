$(document).ready(function () {
    const API_URL = 'http://localhost:8080/api';

    // tabs navigation! pls work!!
    $('#userTabs a').on('click', function (e) {
        e.preventDefault();
        $(this).tab('show');
    });

    let allRoles = []; // Global variable to store roles

// Function to fetch users and roles
    function fetchUsers() {
        $.ajax({
            url: API_URL + '/admins',
            method: 'GET',
            success: function(data) {
                const users = data.users;
                allRoles = data.roles; // Store roles in the global variable

                // Populate the user table
                let userTableBody = $('#userTableBody');
                userTableBody.empty();
                users.forEach(user => {
                    let roles = user.roles.map(role => role.roleName);
                    let rolesString = JSON.stringify(roles);

                    userTableBody.append(`
                <tr>
                    <td>${user.idUser}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${roles.join(', ')}</td>
                    <td>
                        <button class="btn btn-sm btn-primary editUserBtn" 
                                data-id="${user.idUser}" 
                                data-firstname="${user.firstName}" 
                                data-lastname="${user.lastName}" 
                                data-age="${user.age}" 
                                data-email="${user.email}"
                                data-password="${user.password}" 
                                data-roles='${rolesString}'>edit</button>
                    </td>
                    <td>
                        <button class="btn btn-sm btn-danger deleteUserBtn" 
                                data-id="${user.idUser}" 
                                data-firstname="${user.firstName}" 
                                data-lastname="${user.lastName}" 
                                data-age="${user.age}" 
                                data-email="${user.email}"
                                data-password="${user.password}" 
                                data-roles='${rolesString}'>delete</button>
                    </td>
                </tr>
            `);
                });
            },
            error: function(xhr, status, error) {
                console.error('error fetching users:', status, error);
                console.error('response:', xhr.responseText);
                alert('an error occurred while fetching users :(');
            }
        });
    }

    // roles dropdown!!!!!!!!!!!
    function populateRolesDropdown() {
        const addRolesDropdown = $('#addRoles');
        addRolesDropdown.empty();
        allRoles.forEach(role => {
            addRolesDropdown.append(`<option value="${role.roleName}">${role.roleName}</option>`);
        });
    }

    $('#addUserTab').on('shown.bs.tab', function() {
        populateRolesDropdown();
    });

    // add user!!!
    $('#addUserForm').submit(function(event) {
        event.preventDefault();
        console.log('Add user form submitted!!!!!');
        const userFirstName = $('#addFirstName').val();
        const userLastName = $('#addLastName').val();
        const userAge = $('#addAge').val();
        const userEmail = $('#addEmail').val();
        const userPassword = $('#addPassword').val();
        const userRoles = $('#addRoles').val().map(role => ({ roleName: role }));

        console.log('New user form data:', {
            firstName: userFirstName,
            lastName: userLastName,
            age: userAge,
            email: userEmail,
            password: userPassword,
            roles: userRoles
        });

        $.ajax({
            url: API_URL + '/admins/add',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                firstName: userFirstName,
                lastName: userLastName,
                age: userAge,
                email: userEmail,
                password: userPassword,
                roles: userRoles
            }),
            success: function() {
                $('#addUserForm')[0].reset();
                fetchUsers();
                alert('User added successfully! :)');
            },
            error: function(xhr, status, error) {
                console.error('error adding user:', error);
                console.error('server response:', xhr.responseText);
                alert('error adding user: ' + error);
            }
        });
    });


    function populateEditRolesDropdown(userRoles) {
        const editRolesDropdown = $('#editRoles');
        editRolesDropdown.empty();
        allRoles.forEach(role => {
            const isSelected = userRoles.includes(role.roleName) ? 'selected' : '';
            editRolesDropdown.append(`<option value="${role.roleName}" ${isSelected}>${role.roleName}</option>`);
        });
    }

    // edit user!!!
    $(document).on('click', '.editUserBtn', function() {
        const idUser = $(this).data('id');
        const userFirstName = $(this).data('firstname');
        const userLastName = $(this).data('lastname');
        const userAge = $(this).data('age');
        const userEmail = $(this).data('email');
        const userPassword = $(this).data('password');
        const userRoles = JSON.parse($(this).attr('data-roles'));

        console.log('idUser:', idUser);
        console.log('userFirstName:', userFirstName);
        console.log('userLastName:', userLastName);
        console.log('userAge:', userAge);
        console.log('userEmail:', userEmail);
        console.log('userPassword:', userPassword);
        console.log('userRoles:', userRoles);

        $('#editIdUser').val(idUser);
        $('#editFirstName').val(userFirstName);
        $('#editLastName').val(userLastName);
        $('#editAge').val(userAge);
        $('#editEmail').val(userEmail);
        // $('#editPassword').val(userPassword);

        populateEditRolesDropdown(userRoles);

        $('#editUserModal').modal('show');
    });

    $('#editUserForm').submit(function(event) {
        event.preventDefault();
        const userId = $('#editIdUser').val();
        const userFirstName = $('#editFirstName').val();
        const userLastName = $('#editLastName').val();
        const userAge = $('#editAge').val();
        const userEmail = $('#editEmail').val();
        const userPassword = $('#editPassword').val();
        const userRoles = $('#editRoles').val().map(role => ({ roleName: role }));

        const userData = {
            idUser: userId,
            firstName: userFirstName,
            lastName: userLastName,
            age: userAge,
            email: userEmail,
            password: userPassword,
            roles: userRoles
        };

        console.log('updating user with data:', userData);

        $.ajax({
            url: API_URL + '/admins/update/' + userId,
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(userData),
            success: function() {
                $('#editUserModal').modal('hide');
                fetchUsers();
                alert('user updated successfully! :)');
            },
            error: function(xhr, status, error) {
                console.error('error updating user:', status, error);
                console.error('response:', xhr.responseText);
                alert('an error occurred while updating the user :(');
            }
        });
    });


    // delete user!!!
    $(document).on('click', '.deleteUserBtn', function() {
        const idUser = $(this).data('id');
        const userFirstName = $(this).data('firstname');
        const userLastName = $(this).data('lastname');
        const userAge = $(this).data('age');
        const userEmail = $(this).data('email');
        const userRoles = JSON.parse($(this).attr('data-roles'));

        $('#deleteIdUser').val(idUser);
        $('#deleteFirstName').val(userFirstName);
        $('#deleteLastName').val(userLastName);
        $('#deleteAge').val(userAge);
        $('#deleteEmail').val(userEmail);

        $('#deleteRoles').empty();
        userRoles.forEach(role => {
            $('#deleteRoles').append(`<option value="${role}" selected>${role}</option>`);
        });

        $('#deleteUserModal').modal('show');
    });

    $('#deleteUserForm').submit(function(event) {
        event.preventDefault();
        const userIdDelete = $('#deleteIdUser').val();

        $.ajax({
            url: API_URL + '/admins/delete/' + userIdDelete,
            method: 'DELETE',
            success: function() {
                $('#deleteUserModal').modal('hide');
                fetchUsers();
                alert('User deleted successfully!');
            },
            error: function(xhr, status, error) {
                console.error('error deleting user:', status, error);
                console.error('response:', xhr.responseText);
                alert('an error occurred while deleting the user :(');
            }
        });
    });


    $(document).ready(function() {
        fetchUsers();
    });


});
