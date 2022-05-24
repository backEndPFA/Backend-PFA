$(document).ready(function() {


    // # ===============================
    // # = Nombre des ferme
    // # ===============================
    $.ajax({
        url: 'ferme/count',
        data: '',
        type: 'GET',
        success: function(data) {
            $('#ferme').html(data);
        },
        error: function(jqXHR, textStatus,
            errorThrown) {
            console.log(textStatus);
        }
    });

    // # ===============================
    // # = Nombre des parcelle
    // # ===============================
    $.ajax({
        url: 'parcelle/count',
        data: '',
        type: 'GET',
        success: function(data) {
            $('#parcelle').html(data);
        },
        error: function(jqXHR, textStatus,
            errorThrown) {
            console.log(textStatus);
        }
    });
    // # ===============================
    // # = Nombre des plantes
    // # ===============================
    $.ajax({
        url: 'plant/count',
        data: '',
        type: 'GET',
        success: function(data) {
            $('#plante').html(data);
        },
        error: function(jqXHR, textStatus,
            errorThrown) {
            console.log(textStatus);
        }
    });
    // # ===============================
    // # = temprtaure
    // # ===============================
    $.ajax({
        url: 'grandeur/temperature',
        data: '',
        type: 'GET',
        success: function(data) {
            $('#temperature').html(data);
        },
        error: function(jqXHR, textStatus,
            errorThrown) {
            console.log(textStatus);
        }
    });



    // # ===============================
    // # = Nombre arosage par parcelle
    // # ===============================
    $.ajax({
        url: 'parcelle/countarosage',
        contentType: "application/json",
        dataType: "json",
        data: '',
        type: 'GET',
        async: false,
        success: function(data) {
            console.log(data);

            var labels = new Array();
            var dt = new Array();

            Object.keys(data).forEach(key => {
                labels.push(key); // returns the keys in an object
                dt.push(data[key]); // returns the appropriate value
            });

            var ctx = document.getElementById('byYear').getContext('2d');
            var byYear = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        data: dt,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    title: {
                        display: true,
                        text: 'Nombre d\'arosage par parcelle',
                        fontSize: 21,
                        padding: 20,
                        fontFamily: 'Calibri',
                    },
                    legend: {
                        display: false
                    },
                    type: 'line',
                    scales: {

                        yAxes: [{
                            ticks: {
                                beginAtZero: true
                            },
                            scaleLabel: {
                                display: true,
                                labelString: 'Nombre d\'arosage'
                            }
                        }],
                        xAxes: [{
                            scaleLabel: {
                                display: true,
                                labelString: 'Parcelle'
                            }
                        }],
                    }
                }
            });
        },
        error: function(jqXHR, textStatus,
                        errorThrown) {
            console.log(textStatus);
        }
    });
    // # ===============================
    // # = Nombre des parcelles par ferme
    // # ===============================
    $.ajax({
        url: 'parcelle/countparcelle',
        contentType: "application/json",
        dataType: "json",
        data: '',
        type: 'GET',
        async: false,
        success: function(data) {
            console.log(data);

            var labels = new Array();
            var dt = new Array();

            Object.keys(data).forEach(key => {
                labels.push(key); // returns the keys in an object
                dt.push(data[key]); // returns the appropriate value
            });

            var ctx = document.getElementById('myChart').getContext('2d');
            var myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        data: dt,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    title: {
                        display: true,
                        text: 'Nombre des parcelles par ferme',
                        fontSize: 21,
                        padding: 20,
                        fontFamily: 'Calibri',
                    },
                    legend: {
                        display: false
                    },
                    type: 'line',
                    scales: {

                        yAxes: [{
                            ticks: {
                                beginAtZero: true
                            },
                            scaleLabel: {
                                display: true,
                                labelString: 'Nombre des parcelles'
                            }
                        }],
                        xAxes: [{
                            scaleLabel: {
                                display: true,
                                labelString: 'Ferme'
                            }
                        }],
                    }
                }
            });
        },
        error: function(jqXHR, textStatus,
            errorThrown) {
            console.log(textStatus);
        }
    });
});