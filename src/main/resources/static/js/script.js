function setState() {
    const issueId = document.getElementById("issue-id").value;
    if (!issueId) {
        return;
    }

    const newState = document.getElementById("state-select").value;

    axios.patch(`/issue/state/${issueId}`, {state: newState}).then(response => {
        console.log(response);
    }).catch(error => {
        console.log(error);
    })
}