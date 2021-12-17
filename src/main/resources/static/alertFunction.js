vex.defaultOptions.className = 'vex-theme-top';
function showDialog(cb) {
    vex.dialog.open({
        input: ` 
                <button>LOGIN IN</button>
                <button>LOGIN IN</button>
                `,
        buttons: [
            /*$.extend({}, vex.dialog.buttons.YES, {
                text: 'Оставить отзыв',
                click: function ($vexContent, event) {
                    let textArea = document.getElementById("alertWindow_review");
                    console.log(textArea.value);
                }
            }),
            $.extend({}, vex.dialog.buttons.NO, {
                text: 'Понятно',
            })*/
        ]
    })
    /*vex.dialog.open({
        message: 'Ожидайте, с Вами в скором времени свяжутся.',
        /!*input: '<textarea name="notes" rows="6", cols="80"></textarea>',*!/
        buttons: [
            $.extend({}, vex.dialog.buttons.YES, {
                text: 'Понятно'
            }),
            /!*$.extend({}, vex.dialog.buttons.NO, {
                text: 'Cancel'
            })*!/
        ],
        callback: function(data) {
            if (data) {
                console.log(data.notes);
                cb(data.notes);
            }
        }
    });*/
}