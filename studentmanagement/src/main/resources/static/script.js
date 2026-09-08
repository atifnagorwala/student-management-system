// ==========================================
// STORE ALL STUDENTS
// ==========================================

let allStudents = [];


// ==========================================
// LOAD ALL STUDENTS
// ==========================================

function loadStudents() {

    fetch("/students")

        .then(response => {

            if (!response.ok) {

                throw new Error(
                    "Failed to load students"
                );

            }

            return response.json();

        })

        .then(students => {

            // Store all students

            allStudents = students;


            // Display all students

            displayStudents(students);

        })

        .catch(error => {

            console.error(
                "Error loading students:",
                error
            );

            alert(
                "Unable to load students. " +
                "Make sure Spring Boot and MySQL are running."
            );

        });

}


// ==========================================
// DISPLAY STUDENTS
// ==========================================

function displayStudents(students) {

    const table =
        document.getElementById(
            "studentTable"
        );


    // Check table exists

    if (!table) {

        console.error(
            "studentTable not found!"
        );

        return;

    }


    // Clear existing rows

    table.innerHTML = "";


    // No students found

    if (
        !students ||
        students.length === 0
    ) {

        table.innerHTML = `

            <tr>

                <td
                    colspan="4"
                    class="empty-message">

                    No students found

                </td>

            </tr>

        `;

        return;

    }


    // Display students

    students.forEach(student => {

        const row =
            document.createElement("tr");


        row.innerHTML = `

            <td>
                ${student.roll}
            </td>

            <td>
                ${student.name}
            </td>

            <td>
                ${student.branch}
            </td>

            <td>

                <button
                    type="button"
                    class="edit-button"
                    onclick="editStudent(${student.roll})">

                    Edit

                </button>


                <button
                    type="button"
                    class="delete-button"
                    onclick="deleteStudent(${student.roll})">

                    Delete

                </button>

            </td>

        `;


        table.appendChild(row);

    });

}


// ==========================================
// ADD STUDENT
// ==========================================

function addStudent(event) {

    event.preventDefault();


    // Get form values

    const roll =
        document
            .getElementById("roll")
            .value;


    const name =
        document
            .getElementById("name")
            .value;


    const branch =
        document
            .getElementById("branch")
            .value;


    // ==========================================
    // VALIDATION
    // ==========================================

    if (
        roll === "" ||
        name.trim() === "" ||
        branch.trim() === ""
    ) {

        alert(
            "Please fill all fields."
        );

        return;

    }


    if (
        Number(roll) <= 0
    ) {

        alert(
            "Roll number must be greater than 0."
        );

        return;

    }


    if (
        name.trim().length < 2
    ) {

        alert(
            "Name must contain at least 2 characters."
        );

        return;

    }


    if (
        branch.trim().length < 2
    ) {

        alert(
            "Branch must contain at least 2 characters."
        );

        return;

    }


    // ==========================================
    // CREATE OBJECT
    // ==========================================

    const student = {

        roll: Number(roll),

        name: name.trim(),

        branch: branch.trim()

    };


    console.log(
        "Sending student:",
        student
    );


    // ==========================================
    // POST REQUEST
    // ==========================================

    fetch("/students", {

        method: "POST",

        headers: {

            "Content-Type":
                "application/json"

        },

        body:
            JSON.stringify(student)

    })

        .then(async response => {

            const data =
                await response.text();


            if (!response.ok) {

                throw new Error(data);

            }


            return data;

        })

        .then(data => {

            console.log(
                "Student added:",
                data
            );


            alert(
                "Student added successfully!"
            );


            // Clear form

            document
                .getElementById(
                    "studentForm"
                )
                .reset();


            // Reload table

            loadStudents();

        })

        .catch(error => {

            console.error(
                "Error adding student:",
                error
            );


            alert(
                error.message ||
                "Unable to add student."
            );

        });

}


// ==========================================
// EDIT STUDENT
// ==========================================

function editStudent(roll) {

    // Find current student

    const currentStudent =
        allStudents.find(
            student =>
                Number(student.roll) ===
                Number(roll)
        );


    // Current name

    const currentName =
        currentStudent
            ? currentStudent.name
            : "";


    // Current branch

    const currentBranch =
        currentStudent
            ? currentStudent.branch
            : "";


    // Ask new name

    const newName =
        prompt(
            "Enter new name:",
            currentName
        );


    // User cancelled

    if (
        newName === null
    ) {

        return;

    }


    // Validate name

    if (
        newName.trim() === ""
    ) {

        alert(
            "Name cannot be empty."
        );

        return;

    }


    if (
        newName.trim().length < 2
    ) {

        alert(
            "Name must contain at least 2 characters."
        );

        return;

    }


    // Ask new branch

    const newBranch =
        prompt(
            "Enter new branch:",
            currentBranch
        );


    // User cancelled

    if (
        newBranch === null
    ) {

        return;

    }


    // Validate branch

    if (
        newBranch.trim() === ""
    ) {

        alert(
            "Branch cannot be empty."
        );

        return;

    }


    if (
        newBranch.trim().length < 2
    ) {

        alert(
            "Branch must contain at least 2 characters."
        );

        return;

    }


    // ==========================================
    // UPDATED STUDENT
    // ==========================================

    const updatedStudent = {

        roll: Number(roll),

        name: newName.trim(),

        branch: newBranch.trim()

    };


    console.log(
        "Updating student:",
        updatedStudent
    );


    // ==========================================
    // PUT REQUEST
    // ==========================================

    fetch(
        `/students/${roll}`,
        {

            method: "PUT",

            headers: {

                "Content-Type":
                    "application/json"

            },

            body:
                JSON.stringify(
                    updatedStudent
                )

        }
    )

        .then(async response => {

            const data =
                await response.text();


            if (!response.ok) {

                throw new Error(data);

            }


            return data;

        })

        .then(data => {

            console.log(
                "Student updated:",
                data
            );


            alert(
                "Student updated successfully!"
            );


            // Reload students

            loadStudents();

        })

        .catch(error => {

            console.error(
                "Error updating student:",
                error
            );


            alert(
                error.message ||
                "Unable to update student."
            );

        });

}


// ==========================================
// DELETE STUDENT
// ==========================================

function deleteStudent(roll) {

    const confirmDelete =
        confirm(
            "Are you sure you want to delete student " +
            roll +
            "?"
        );


    if (!confirmDelete) {

        return;

    }


    // ==========================================
    // DELETE REQUEST
    // ==========================================

    fetch(
        `/students/${roll}`,
        {

            method: "DELETE"

        }
    )

        .then(async response => {

            const data =
                await response.text();


            if (!response.ok) {

                throw new Error(data);

            }


            return data;

        })

        .then(message => {

            console.log(
                "Student deleted:",
                message
            );


            alert(
                "Student deleted successfully!"
            );


            // Reload students

            loadStudents();

        })

        .catch(error => {

            console.error(
                "Error deleting student:",
                error
            );


            alert(
                error.message ||
                "Unable to delete student."
            );

        });

}


// ==========================================
// SEARCH STUDENTS
// ==========================================

function searchStudents() {

    const searchInput =
        document.getElementById(
            "searchInput"
        );


    // Check input exists

    if (!searchInput) {

        console.error(
            "searchInput not found!"
        );

        return;

    }


    // Get search text

    const searchText =
        searchInput.value
            .toLowerCase()
            .trim();


    console.log(
        "Searching for:",
        searchText
    );


    // ==========================================
    // EMPTY SEARCH
    // ==========================================

    if (
        searchText === ""
    ) {

        displayStudents(
            allStudents
        );

        return;

    }


    // ==========================================
    // FILTER STUDENTS
    // ==========================================

    const filteredStudents =
        allStudents.filter(
            student => {


                // Roll

                const roll =
                    String(
                        student.roll
                    )
                        .toLowerCase();


                // Name

                const name =
                    String(
                        student.name || ""
                    )
                        .toLowerCase();


                // Branch

                const branch =
                    String(
                        student.branch || ""
                    )
                        .toLowerCase();


                // Check all three

                return (

                    roll.includes(
                        searchText
                    )

                    ||

                    name.includes(
                        searchText
                    )

                    ||

                    branch.includes(
                        searchText
                    )

                );

            }
        );


    console.log(
        "Matching students:",
        filteredStudents
    );


    // Display results

    displayStudents(
        filteredStudents
    );

}


// ==========================================
// PAGE INITIALIZATION
// ==========================================

document.addEventListener(
    "DOMContentLoaded",
    function () {


        // ==================================
        // STUDENT FORM
        // ==================================

        const studentForm =
            document.getElementById(
                "studentForm"
            );


        if (studentForm) {

            studentForm.addEventListener(
                "submit",
                addStudent
            );

        }


        // ==================================
        // SEARCH BOX
        // ==================================

        const searchInput =
            document.getElementById(
                "searchInput"
            );


        if (!searchInput) {

            console.error(
                "ERROR: searchInput element not found!"
            );

        }
        else {

            searchInput.addEventListener(
                "input",
                searchStudents
            );

        }


        // ==================================
        // LOAD STUDENTS
        // ==================================

        loadStudents();

    }
);