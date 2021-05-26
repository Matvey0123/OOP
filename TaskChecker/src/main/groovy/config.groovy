group{
    name = "19213"
    students = [
            {
                name = "Matvey"
                gitURL = "https://github.com/Matvey0123/OOP.git"
            }
    ]
    tasks = [
            {
                name = "Task_1_1"
                deadline = "30.09.2020"
                gradingId = "deadlinePoints"
                points = 5
            },
            {
                name = "Task_1_2"
                deadline = "1.09.2020"
                gradingId = "deadlinePoints"
                points = 9
            }
    ]
    lessons = [
            {
                lessonDate = "4.09.2020"
            },
            {
                lessonDate = "11.09.2020"
            },
            {
                lessonDate = "18.09.2020"
            }
    ]
    controlPoints = [
            {
                name = "FirstControlWeek"
                date = "15.10.2020"
            },
            {
                name = "SecondControlWeek"
                date = "20.11.2020"
            }
    ]
}


grader{
    id = "deadlinePoints"
    evaluator = {parameters -> parameters.getDaysPastDeadline() > 0 && parameters.isAllTestsPassed()? 0 : parameters.getPointsForTask()}
}