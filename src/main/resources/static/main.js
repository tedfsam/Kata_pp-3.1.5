let allRoles;
let allUsers;

getAllRoles();
getAllUsers();
getCurrent();

function getCurrent() {
    $.ajax({
        url: "/rest/admin/getCurrentUser",
        type: "GET",
        dataType: "json"
    }).done((msg) => {
        let user = JSON.parse(JSON.stringify(msg));
        $("#current-user-table tbody").empty().append(
            $("<tr>").append(
                $("<td>").text(user.id),
                $("<td>").text(user.name),
                $("<td>").text(user.lastName),
                $("<td>").text(user.age),
                $("<td>").text(user.email),
                $("<td>").text(user.roles)
            ));
    }).fail(() => {
        alert("Error Get current User!")
    })
}

$('[href="#v-pills-user"]').on('show.bs.tab', (e) => {
    getCurrent();
});

function getAllRoles() {
    $.ajax({
        url: "/rest/admin/roles",
        type: "GET",
        dataType: "json"
    }).done((msg) => {
        allRoles = JSON.parse(JSON.stringify(msg));
    })
}

function getAllUsers() {
    $.ajax({
        url: "/rest/admin/users",
        type: "GET",
        dataType: "json"
    }).done((msg) => {
        allUsers = JSON.parse(JSON.stringify(msg));
        $("#all-users-table tbody").empty();
        $.each(allUsers, (i, user) => {
            $("#all-users-table tbody").append(
                $("<tr>").append(
                    $("<td>").text(user.id),
                    $("<td>").text(user.name),
                    $("<td>").text(user.lastName),
                    $("<td>").text(user.age),
                    $("<td>").text(user.email),
                    $("<td>").text(user.roles),
                    $("<td>").append("<button type='button' data-toggle='modal' class='btn-info btn'" +
                        "data-target='#editUserModal' data-user-id='" + user.id + "'>Edit</button>"),
                    $("<td>").append("<button type='button' data-toggle='modal' class='btn btn-danger'" +
                        "data-target='#deleteUserModal' data-user-id='" + user.id + "'>Delete</button>")
                )
            );
        });
    });
}

$('[href="#v-pills-admin"]').on('show.bs.tab', (e) => {
    getAllUsers();
});

$("#editUserModal").on('show.bs.modal', (e) => {
    let userId = $(e.relatedTarget).data("user-id");

    $.ajax({
        url: "/rest/admin/users/" + userId,
        type: "GET",
        dataType: "json"
    }).done((msg) => {

        let user = JSON.parse(JSON.stringify(msg));

        $("#idEdit").empty().val(user.id);
        $("#firstNameEdit").empty().val(user.name);
        $("#lastNameEdit").empty().val(user.lastName);
        $("#ageEdit").empty().val(user.age);
        $("#emailEdit").empty().val(user.email);
        $("#rolesEdit").empty();
        $.each(allRoles, (i, role) => {
            $("#rolesEdit").append(
                $("<option>").text(role)
            )
        });
        $("#rolesInputEdit").val(user.roles);
    });
});

$('[href="#newUser"]').on('show.bs.tab', (e) => {
    $(() => {
        $("#firstNameNewUser").empty().val("");
        $("#lastNameNewUser").empty().val("");
        $("#ageNewUser").empty().val("");
        $("#emailNewUser").empty().val("");
        $("#passwordNewUser").empty().val("");
        $("#rolesNewUser").empty().val("");
        $.each(allRoles, (i, role) => {
            $("#rolesNewUser").append(
                $("<option>").text(role)
            )
        });
    })
});

$("#buttonInputNewSubmit").on('click', (e) => {
    e.preventDefault();

    let newUser = {
        name: $("#firstNameNewUser").val(),
        lastName: $("#lastNameNewUser").val(),
        age: $("#ageNewUser").val(),
        email: $("#emailNewUser").val(),
        password: $("#passwordNewUser").val(),
        roles: $("#rolesNewUser").val()
    };

    $.ajax({
        url: "/rest/admin/users",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(newUser)
    }).done((msgSave) => {
        getAllUsers();
        $('#AdminTabs a[href="#usersTable"]').tab('show');
    })
});

$("#buttonEditSubmit").on('click', (e) => {
    e.preventDefault();

    let editUser = {
        id: $("#idEdit").val(),
        name: $("#firstNameEdit").val(),
        lastName: $("#lastNameEdit").val(),
        age: $("#ageEdit").val(),
        email: $("#emailEdit").val(),
        password: $("#passwordEdit").val(),
        roles: $("#rolesEdit").val()
    };

    $.ajax({
        url: "/rest/admin/users",
        type: "PUT",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(editUser)
    }).done((msgUpdate) => {
        $("#editUserModal").modal('hide');
        getAllUsers();
    })
});

$("#deleteUserModal").on('show.bs.modal', (e) => {
    let userId = $(e.relatedTarget).data("user-id");

    $.ajax({
        url: "/rest/admin/users/" + userId,
        type: "GET",
        dataType: "json"
    }).done((msg) => {
        let user = JSON.parse(JSON.stringify(msg));

        $("#idDelete").empty().val(user.id);
        $("#firstNameDelete").empty().val(user.name);
        $("#lastNameDelete").empty().val(user.lastName);
        $("#ageDelete").empty().val(user.age);
        $("#emailDelete").empty().val(user.email);
        $("#rolesDelete").empty();
        $.each(allRoles, (i, role) => {
            $("#rolesDelete").append(
                $("<option>").text(role)
            )
        });
        $("#rolesInputDelete").val(user.roles);

        $("#buttonDeleteSubmit").on('click', (e) => {
            e.preventDefault();

            $.ajax({
                url: "/rest/admin/users/" + userId,
                type: "DELETE",
                dataType: "text"
            }).done((deleteMsg) => {
                $("#deleteUserModal").modal('hide');
                getAllUsers();
            })
        })
    });
});