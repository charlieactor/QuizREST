function main() {
  console.log("loaded");
  $.ajax({
      type: "GET",
      url: "api/quizzes",
      dataType: "json"
    })
    .done(function(data, status) {
      console.log(data);
      data.forEach(function(val, idx) {
        console.log(val);
        var trow = $('<tr>');
        var quiz = $('<td>').text(val.name);
        var view = $('<td>').text("View").attr("id", val.id).attr('class', val.name);
        var edit = $('<td>').text("Edit").attr("id", val.id);
        var destroy = $('<td>').text("Delete").attr("id", val.id);
        trow.append(quiz);
        trow.append(view);
        trow.append(edit);
        trow.append(destroy);
        $('#quizBod').append(trow);

        view.click(function(e) {
          viewQuiz(val.name, e.target.id);
        });
        edit.click(function(e) {
          editQuiz(val.name, e.target.id);
        });
        destroy.click(function(e) {
          deleteQuiz(val.name, e.target.id);
        });
      });

    });
}

function deleteQuiz(quizzesName, quizId) {
  if (confirm("Do you really want to delete " + quizzesName + "?")) {
    $.ajax({
      type : "DELETE",
      url  : "api/quizzes/" + quizId
    }).done(function() {
      $('#quizBod').empty();
      main();
    })
  }
}

function editQuiz(quizzesName, quizId) {
  $('#quizTable').hide();
  $('#createQuiz').hide();

  var div = $('#content');
  var form = $('<form>');
  div.append(form);
  var qname = $('<input>').attr('name', 'name').attr('value', quizzesName);
  var submit = $('<input>').attr('type', 'submit');
  form.append(qname, submit);

  submit.click(function(e) {
    e.preventDefault();
    var updatedQuizArr = $('form').serializeArray();
    var updatedQuiz = {};
    updatedQuizArr.forEach(function(val, idx) {
      updatedQuiz[val.name] = val.value;
    });
    $.ajax({
      type: "PUT",
      url: "api/quizzes/" + quizId,
      dataType: "json",
      contentType: 'application/json',
      data: JSON.stringify(updatedQuiz)
    }).done(function(data) {
      $('#quizBod').empty();
      $('#quizTable').show();
      $('#createQuiz').show();
      div.empty();
      main();
    });
  });
};


function viewQuiz(quizzesName, quizId) {
  $('#quizTable').hide();
  $('#createQuiz').hide();
  getQuizQuestions(quizId)
    .done(function(data) {
      console.log(data);
      var div = $('#content');
      var quizName = $('<h1>').text(quizzesName);
      div.append(quizName)
      var ul = $('<ol>');
      div.append(ul);
      data.forEach(function(val, idx) {
        var li = $('<li>');
        li.text(val.questionText);
        ul.append(li);
      })
      var listQuizzes = $('<button>').text("List all Quizzes");
      div.append(listQuizzes);
      listQuizzes.click(function(e) {
        div.empty();
        $('#quizTable').show();
        $('#createQuiz.show');
      })
    })

}

function getQuizQuestions(quizId) {
  return $.ajax({
    type: "GET",
    url: "api/quizzes/" + quizId + "/questions",
    dataType: "json"
  });
}

var createQuizButton = $('#createQuiz');

createQuizButton.click(function(e) {
  $('#quizTable').hide();
  createQuizButton.hide();
  var div = $('#content');
  var form = $('<form>');
  div.append(form);
  var inputQuizName = $('<input>').attr('name', 'name');
  var submit = $('<input>').attr('type', 'submit').attr('value', 'Create this Quiz!');
  form.append(inputQuizName);
  form.append(submit);

  submit.click(function(e) {
    e.preventDefault();
    var newQuizArr = $('form').serializeArray();
    var newQuiz = {};
    newQuizArr.forEach(function(val, idx) {
      newQuiz[val.name] = val.value;
    });
    $.ajax({
      type: "POST",
      url: "api/quizzes",
      dataType: "json",
      contentType: 'application/json',
      data: JSON.stringify(newQuiz)
    }).done(function(data) {
      div.empty();
      $('#quizBod').empty()
      $('#quizTable').show();
      createQuizButton.show();
      main();
    });
  });
});
