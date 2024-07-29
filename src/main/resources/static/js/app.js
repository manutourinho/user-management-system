$(document).ready(function () {
    const API_URL = 'http://localhost:8080/api';
    // const editUserModal = document.getElementById('editUserModal');
    // const deleteUserModal = document.getElementById('deleteUserModal');
    // const editUserForm = document.getElementById('editUserForm');

    // tabs navigation! pls work!!
    $('#userTabs a').on('click', function (e) {
        e.preventDefault();
        $(this).tab('show');
    });

    // fetch and display users
    function fetchUsers() {
        $.ajax({
            url: API_URL + '/admins',
            method: 'GET',
            success: function(data) {
                let userTableBody = $('#userTableBody');
                userTableBody.empty();
                data.forEach(user => {
                    let roles = user.roles.map(role => (role.roleName).substring(5)).join(', ');

                    userTableBody.append(`
                <tr>
                    <td>${user.idUser}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${roles}</td>
                    <td>
                        <button id="editUserBtn" class="btn btn-sm btn-primary editUserBtn" 
                                data-id="${user.idUser}" 
                                data-firstname="${user.firstName}" 
                                data-lastname="${user.lastName}" 
                                data-age="${user.age}" 
                                data-email="${user.email}" 
                                data-roles='${roles}'>
                            edit
                        </button>

                    </td>
                    <td>
                        <button class="btn btn-sm btn-danger deleteUserBtn" data-id="${user.idUser}">delete</button>
                    </td>
                </tr>
            `);
                });
            },
            error: function(xhr, status, error) {
                console.error('Error fetching users:', status, error);
                console.error('Response:', xhr.responseText);
                alert('An error occurred while fetching users.');
            }
        });
    }

    $(document).ready(function() {
        fetchUsers();
    });


    // add User!!!
    $('#addUserForm').submit(function (event) {
        event.preventDefault();
        console.log('add user form submitted!!!!!');
        const userFirstName = $('#addFirstName').val();
        const userLastName = $('#addLastName').val();
        const userAge = $('#addAge').val();
        const userEmail = $('#addEmail').val();
        const userPassword = $('#addPassword').val();
        const selectedRoles = $('#addRoles').val();

        const userRoles = selectedRoles.map(roleId => ({idRole: roleId}))

        console.log('new user form data:', {
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
                idRole: userRoles
            }),
            success: function () {
                $('#addUserForm')[0].reset();
                fetchUsers();
                alert('user added successfully! :)');
            },
            error: function (xhr, status, error) {
                console.error('error adding user:', error);
                console.error('server response:', xhr.responseText);
                alert('error adding user: ' + error);
            }
        });
    });


    // edit user~~~~~~~
    // v.1
    $(document).on('click', '.editUserBtn', function() {
        const idUser = $(this).data('id');
        const userFirstName = $(this).data('firstname');
        const userLastName = $(this).data('lastname');
        const userAge = $(this).data('age');
        const userEmail = $(this).data('email');
        const userRoles = JSON.parse($(this).attr('data-roles'));

        console.log('idUser:', idUser);
        console.log('userFirstName:', userFirstName);
        console.log('userLastName:', userLastName);
        console.log('userAge:', userAge);
        console.log('userEmail:', userEmail);
        console.log('userRoles:', userRoles);

        $('#editUserId').val(idUser);
        $('#editFirstName').val(userFirstName);
        $('#editLastName').val(userLastName);
        $('#editAge').val(userAge);
        $('#editEmail').val(userEmail);

        $('#editRoles').empty();
        userRoles.forEach(role => {
            $('#editRoles').append(`<option value="${role}" selected>${role}</option>`);
        });

        $('#editUserModal').modal('show');
    });

    $('#editUserForm').on('submit', function(event) {
        event.preventDefault();
        const idUser = $('#editUserId').val();
        const userFirstName = $('#editFirstName').val();
        const userLastName = $('#editLastName').val();
        const userAge = $('#editAge').val();
        const userEmail = $('#editEmail').val();
        const userRoles = $('#editRoles').val().map(role => ({ name: role }));

        $.ajax({
            url: API_URL + '/admins/update/' + idUser,
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({
                firstName: userFirstName,
                lastName: userLastName,
                age: userAge,
                email: userEmail,
                roles: userRoles
            }),
            success: function() {
                $('#editUserModal').modal('hide');
                fetchUsers();
                alert('User updated successfully!');
            }
        });
    });






    // delete User
    // $(document).on('click', '.deleteUserBtn', function () {
    //     const idUser = $(this).data('id');
    //     const userFirstName = $(this).data('firstname');
    //     const userLastName = $(this).data('lastname');
    //     const userAge = $(this).data('age');
    //     const userEmail = $(this).data('email');
    //     const userPassword = $(this).data('password');
    //     const userRoles = JSON.parse($(this).attr('data-roles'));
    //
    //     console.log('idUser:', idUser);
    //     console.log('userFirstName:', userFirstName);
    //     console.log('userLastName:', userLastName);
    //     console.log('userAge:', userAge);
    //     console.log('userEmail:', userEmail);
    //     console.log('userPassword:', userPassword);
    //     console.log('userRoles:', userRoles);
    //
    //     $('#deleteUserId').val(idUser);
    //     $('#deleteFirstName').val(userFirstName);
    //     $('#deleteLastName').val(userLastName);
    //     $('#deleteAge').val(userAge);
    //     $('#deleteEmail').val(userEmail);
    //     // $('#deletePassword').val(userPassword);
    //
    //     $('#editRoles').empty();
    //     userRoles.forEach(role => {
    //         $('#deleteRoles').append(`<option value="${role}" selected>${role}</option>`);
    //     });
    //
    //     $('#deleteUserModal').modal('show');
    // });
    //
    // $('#deleteUserForm').on('submit', function(event) {
    //     event.preventDefault();
    //     const idUser = $('#deleteUserId').val();
    //     const userFirstName = $('#deleteFirstName').val();
    //     const userLastName = $('#deleteLastName').val();
    //     const userAge = $('#deleteAge').val();
    //     const userEmail = $('#deleteEmail').val();
    //     const userPassword = $('#deletePassword').val();
    //     const userRoles = $('#deleteRoles').val().map(role => ({ name: role }));
    //
    //     $.ajax({
    //         url: API_URL + '/admins/delete/' + idUser,
    //         method: 'DELETE',
    //         contentType: 'application/json',
    //         data: JSON.stringify({
    //             firstName: userFirstName,
    //             lastName: userLastName,
    //             age: userAge,
    //             email: userEmail,
    //             password: userPassword,
    //             roles: userRoles
    //         }),
    //         success: function() {
    //             $('#deleteUserModal').modal('hide');
    //             fetchUsers();
    //             alert('User deleted successfully!');
    //         }
    //     });
    // });


});


// const
//     firstName = document.getElementById("firstName"),
//     lastName = document.getElementById("lastName"),
//     age = document.getElementById("age"),
//     email = document.getElementById("email"),
//     password = document.getElementById("password"),
//     role = document.getElementById("roles"),
//     submitBtn = document.querySelector(".submit"),
//     userInfo = document.getElementById("data"),
//     modal = document.getElementById("userForm"),
//     modalTitle = document.querySelector("#userForm .modal-title"),
//     newUserTab = document.querySelector(".newUser");
//
// let getData = localStorage.getItem('userProfile') ? JSON.parse(localStorage.getItem('userProfile')) : [];
//
//
// let isEdit = false, editId;
// showInfo();
//
// newUserTab.addEventListener('click', ()=> {
//     submitBtn.innerText = 'Submit';
//         modalTitle.innerText = "Fill the Form";
//     isEdit = false;
//     form.reset();
// })
//
//
//
//
// function showInfo(){
//     document.querySelectorAll('.userDetails').forEach(info => info.remove())
//     getData.forEach(function (user, index)  {
//         let row = `
//          <tr>
//             <td class="text-center">${index+1}</td>
//             <td class="text-center">${user.userFirstName}</td>
//             <td class="text-center">${user.userLastName}</td>
//             <td class="text-center">${user.userAge}</td>
//             <td class="text-center">${user.userEmail}</td>
//             <td class="text-center">${user.userPassword}</td>
//             <td class="text-center">${user.userRoles}</td>
//
//
//             <td class="text-center">
//                 <button class="btn btn-success" onclick="readInfo('${user.userFirstName}', '${user.userLastName}', '${user.userAge}', '${user.userEmail}', '${user.userPassword}', '${user.userRoles}')" data-bs-toggle="modal" data-bs-target="#readData"><i class="bi bi-eye"></i></button>
//
//                 <button class="btn btn-primary" onclick="editInfo(${index}, '${user.userFirstName}', '${user.userLastName}', '${user.userAge}', '${user.userEmail}', '${user.userPassword}', '${user.userRoles}')" data-bs-toggle="modal" data-bs-target="#userForm"><i class="bi bi-pencil-square"></i></button>
//
//                 <button class="btn btn-danger" onclick="deleteInfo(${index})"><i class="bi bi-trash"></i></button>
//
//             </td>
//         </tr>`
//
//     })
// }
// showInfo();
//
// const form = document.getElementById('newUserForm');
// form.addEventListener('submit', function (e) {
//     e.preventDefault();
//
//     const firstName = document.getElementById('firstName_add').value;
//     const lastName = document.getElementById('lastName_add').value;
//     const age = document.getElementById('age_add').value;
//     const email = document.getElementById('email_add').value;
//     const password = document.getElementById('password_add').value;
//     const roles = document.getElementById('roles_add').value.split(',');
//
//     const newUser = {
//         userFirstName: firstName,
//         userLastName: lastName,
//         userAge: age,
//         userEmail: email,
//         userPassword: password,
//         userRoles: roles
//     };
//
//     getData.push(newUser);
//     localStorage.setItem('userProfile', JSON.stringify(getData));
//
//     // Reset form fields
//     form.reset();
//
//     // Update the displayed user list
//     showInfo();
// });
//
//
// function readInfo(firstName, lastName, age, email, password, roles){
//         document.querySelector('#showFirstName').value = firstName,
//             document.querySelector('#showLastName').value = lastName,
//         document.querySelector("#showAge").value = age,
//         document.querySelector("#showEmail").value = email,
//             document.querySelector('#showPassword').value = password,
//         document.querySelector("#showRoles").value = roles;
// }
//
//
// function editInfo(index){
//     const user = getData[index];
//
//     document.getElementById('editFirstName').value = user.userFirstName;
//     document.getElementById('editLastName').value = user.userLastName;
//     document.getElementById('editAge').value = user.userAge;
//     document.getElementById('editEmail').value = user.userEmail;
//
//     // submitBtn.innerText = "Update";
//     // modalTitle.innerText = "Update The Form";
//     document.getElementById('editUserId').value = index;
// }
//
//
// function deleteInfo(index){
//     if(confirm("Are you sure want to delete?")){
//         getData.splice(index, 1);
//         localStorage.setItem("userProfile", JSON.stringify(getData));
//         showInfo();
//     }
// }
//
//
// form.addEventListener('submit', (e)=> {
//     e.preventDefault();
//
//     const information = {
//         userFirstName: firstName.value,
//         userLastName: lastName.value,
//         userAge: age.value,
//         userEmail: email.value,
//         userPassword: password.value,
//         userRoles: role.value,
//
//     }
//
//     if(!isEdit){
//         getData.push(information);
//     }
//     else{
//         isEdit = false;
//         getData[editId] = information;
//     }
//
//     localStorage.setItem('userProfile', JSON.stringify(getData));
//
//     submitBtn.innerText = "Submit";
//     modalTitle.innerHTML = "Fill The Form";
//
//     showInfo();
//
//     form.reset();
//
//
// })


//
// // Function to fetch user data from the server
// async function fetchUserData() {
//     try {
//         const response = await fetch('/admin/users');
//         const userData = await response.json();
//         return userData;
//     } catch (error) {
//         console.error('Error fetching user data:', error);
//         throw error;
//     }
// }
//
// // Function to generate the HTML for a user row
// function createUserRow(user) {
//     return `
//     <tr>
//       <td>${user.idUser}</td>
//       <td>${user.firstName}</td>
//       <td>${user.lastName}</td>
//       <td>${user.email}</td>
//       <td>${user.age}</td>
//       <td>${user.roles.join(', ')}</td>
//       <td>
//         <button class="btn btn-primary btn-edit" data-user-id="${user.idUser}">edit</button>
//         <button class="btn btn-danger btn-delete" data-user-id="${user.idUser}">delete</button>
//       </td>
//     </tr>
//   `;
// }
//
// // Function to populate the user table
// async function populateUserTable() {
//     try {
//         const userList = await fetchUserData();
//         const userTableBody = document.getElementById('user-table-body');
//         userTableBody.innerHTML = '';
//
//         userList.forEach(user => {
//             const userRow = createUserRow(user);
//             userTableBody.innerHTML += userRow;
//         });
//
//         // Add event listeners for edit and delete buttons
//         const editButtons = document.querySelectorAll('.btn-edit');
//         editButtons.forEach(button => {
//             button.addEventListener('click', () => {
//                 const idUser = button.dataset.idUser;
//                 showEditUserModal(idUser);
//             });
//         });
//
//         const deleteButtons = document.querySelectorAll('.btn-delete');
//         deleteButtons.forEach(button => {
//             button.addEventListener('click', () => {
//                 const idUser = button.dataset.idUser;
//                 showDeleteUserModal(idUser);
//             });
//         });
//     } catch (error) {
//         console.error('Error populating user table:', error);
//     }
// }
//
// // Function to show the edit user modal
// function showEditUserModal(idUser) {
//     // Code to show the edit user modal and populate it with the user data
//     // This could involve fetching the user data and rendering the modal HTML
// }
//
// // Function to show the delete user modal
// function showDeleteUserModal(idUser) {
//     // Code to show the delete user modal and populate it with the user data
//     // This could involve fetching the user data and rendering the modal HTML
// }
//
// // Call the populateUserTable function when the page loads
// document.addEventListener('DOMContentLoaded', populateUserTable);