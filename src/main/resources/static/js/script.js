function getWorkItemReport() {

    fetch('/api/v1/work-items/report', {method: 'GET'})
    .then(function(response) {

        if(response.status == 200) {
            return response.json();
        } else {
            return response.text().then(text => {throw new Error(text)})
        }
    })
    .then(function(json_response) {
        const data = json_response['data'];
        console.log(json_response);

        const table_body = document.getElementById('report-table-body');

        data.forEach(function(entry) {

           const row = document.createElement('tr');

            for (const key in entry) {
                const row_data = document.createElement('td');
                row_data.innerHTML = entry[key];
                row.appendChild(row_data);
            }

            table_body.appendChild(row);
        })
    })
    .catch(function(error) {
        try {
            const json_error = JSON.parse(error.message);
            console.log("Error: ", json_error.message);
            alert(json_error.message);
        } catch (error) {
            console.log(error);
            console.log(error.message);
            alert(error.message);
        }
    });
}

getWorkItemReport();