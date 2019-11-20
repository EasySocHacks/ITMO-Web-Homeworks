window.notify = function(message) {
    $.notify(message, {position: "bottom right"})
};

ajax = function(method, data, onSuccess) {
    $.ajax({
        type: method,
        url: "",
        dataType: "json",
        data: data,
        success: function (response) {
            onSuccess(response);

            if (response["redirect"])
                location.href = response["redirect"];
        }
    });
}