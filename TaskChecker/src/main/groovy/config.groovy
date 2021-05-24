group{
    name = "19213"
    students = [
            {
                name = "Matvey"
                gitUrl = "https://github.com/Matvey0123/OOP.git"
            },
            {
                name = "Unknown"
                gitUrl = "blablabla"
            }
    ]
}
group{
    name = "19214"

}
grader{
    id = "deadlinePoints"
    evaluator = {parameters -> parameters.getDaysPastDeadline() > 0 && parameters.isAllTestsPassed()? 0 : parameters.getPointsForTask()}
}