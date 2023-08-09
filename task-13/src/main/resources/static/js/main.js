function fetchAllBookData() {
    fetch("/api/v1/books")
        .then(response => response.json())
        .then(json => buildMainTable(json))
}

function fetchOneBookData(bookId) {
    fetch("/api/v1/books/" + bookId)
        .then(response => response.json())
        .then(json => fillBookData(json))
}

function buildMainTable(json) {
    json.forEach(book => {
        addRowTable(book);
    });
}

function addRowTable(book) {
        let row = document.getElementById("main-book-table").getElementsByTagName("tbody")[0].insertRow();
        row.setAttribute("bookId", book.id);
        addNameCell(row, book);
        addAuthorCell(row, book);
        addGenreCell(row, book);
        addEditAnchorCell(row, book);
        addCommentsAnchorCell(row, book);
        addDeleteAnchorCell(row, book);
}

function addNameCell(row, book) {
    let cell = row.insertCell();
    cell.append(book.name);
}

function addAuthorCell(row, book) {
    let cell = row.insertCell();
    cell.append(book.author.fullName);
}

function addGenreCell(row, book) {
    let cell = row.insertCell();
    cell.append(book.genre.title);
}

function addEditAnchorCell(row, book) {
    let cell = row.insertCell();
    let anchor = document.createElement("a");
    anchor.setAttribute("href", `/edit/${book.id}`);
    anchor.append("edit");
    cell.append(anchor);
}

function addCommentsAnchorCell(row, book) {
    let cell = row.insertCell();
    let anchor = document.createElement("a");
    anchor.setAttribute("href", `/comments?bookId=${book.id}`);
    anchor.append("view");
    cell.append(anchor);
}

function addDeleteAnchorCell(row, book) {
    let cell = row.insertCell();
    let anchor = document.createElement("a");
    anchor.setAttribute("href", "#");
    anchor.setAttribute("onClick", "deleteBook(this)");
    anchor.append("delete");
    cell.append(anchor);
}

function fillBookData(book) {
    document.getElementById("id-input").value = book.id;
    document.getElementById("name-input").value = book.name;
    document.getElementById("author-input").value = book.author.fullName;
    document.getElementById("genre-input").value = book.genre.title;
}

function updateBook() {
    const bookUpd = {
        id: document.getElementById("id-input").value,
        name: document.getElementById("name-input").value,
        author: {
            fullName: document.getElementById("author-input").value
        },
        genre: {
            title: document.getElementById("genre-input").value
        }
    };

    fetch("/api/v1/books/" + bookUpd.id, {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(bookUpd)
    })
        .then(window.location.replace("/"));
}


function saveBook() {
    const book = {
        name: document.getElementById("book-title-input").value,
        author: {
            fullName: document.getElementById("book-author-input").value
        },
        genre: {
            title: document.getElementById("book-genre-input").value
        }
    };

    fetch("/api/v1/books/", {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(book)
    })
        .then(result => result.json())
        .then(json => addRowTable(json));
}

function deleteBook(anchor) {
    let row = anchor.parentElement.parentElement;
    fetch(`/api/v1/books/${row.getAttribute("bookId")}`, {
        method: "DELETE",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    })
        .then(() => row.remove());
}


function fetchBookCommentsData(bookId) {
    fetch("/api/v1/comments/?bookId=" + bookId)
        .then(response => response.json())
        .then(json => fillCommentsPage(json))
}

function fillCommentsPage(json) {
    buildSimpleBookTable(json.book);
    buildCommentsTable(json.comments);
}

function buildSimpleBookTable(book) {
    let row = document.getElementById("simple-book-table").getElementsByTagName("tbody")[0].insertRow();
    row.setAttribute("bookId", book.id);
    addNameCell(row, book);
    addAuthorCell(row, book);
    addGenreCell(row, book);
}

function buildCommentsTable(comments) {
    comments.forEach(comment => {
        addRowComments(comment);
    })
}

function addRowComments(comment) {
    let row = document.getElementById("comments-table").getElementsByTagName("tbody")[0].insertRow();
    row.setAttribute("id", comment.id);

    row.insertCell().append(comment.nickName);
    row.insertCell().append(comment.text);

    let anchor = document.createElement("a");
    anchor.setAttribute("href", "#");
    anchor.setAttribute("onClick", "deleteComment(this)");
    anchor.append("Delete comment");

    row.insertCell().append(anchor);
}

function deleteComment(anchor) {
    let row = anchor.parentElement.parentElement;
    fetch(`/api/v1/comments/${row.getAttribute("id")}`, {
        method: "DELETE",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    })
        .then(() => row.remove());
}

function saveComment(bookId) {
    let comment = {
        nickName: document.getElementById("input-nickname").value,
        text: document.getElementById("input-text").value
    };

    fetch("api/v1/comments?bookId=" + bookId, {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(comment)
    })
        .then(result => result.json())
        .then(json => addRowComments(json))
        .then(() => cleanInputField());
}

function cleanInputField() {
    document.getElementById("input-nickname").value = "";
    document.getElementById("input-text").value = "";
}

