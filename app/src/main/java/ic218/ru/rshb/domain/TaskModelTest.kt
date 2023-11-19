package ic218.ru.rshb.domain

import ic218.ru.rshb.domain.entity.Culture
import ic218.ru.rshb.domain.entity.Employer
import ic218.ru.rshb.domain.entity.Field
import ic218.ru.rshb.domain.entity.Operation
import ic218.ru.rshb.domain.entity.Priority
import ic218.ru.rshb.domain.entity.Task
import ic218.ru.rshb.domain.entity.TaskField
import ic218.ru.rshb.domain.entity.TaskParam

object TaskModelTest {

    val employerOwner = Employer(
        id = 0,
        nfcid = "",
        username = "Васильев Валерий Александрович",
        usersurname = "",
        groupid = 1,
        jobtitle = "Главный агроном",
        phone = "+7 (967) 765-43-21",
        avatar = "",
        machine = 0,
        agregate = ""
    )

    val employer = Employer(
        id = 0,
        nfcid = "",
        username = "Иванов И.И.",
        usersurname = "",
        groupid = 99,
        jobtitle = "Главный агроном",
        phone = "+7 (967) 111-11-11",
        avatar = "",
        machine = 0, //" RSM 3535 #7089",
        agregate = "Horsh Pronto NT12 #1"
    )

    val tasks = listOf(
        Task(
            id = 182,
            operation = "Посев",
            status = Task.Status.WORK,
            pole = "П108",
            date = "19.11.23",
            owner = employerOwner,
            assigned = employer,
            additionalInfo = "",
            priority = Task.Priority.HIGH,
            params = listOf(
                TaskParam(
                    id = 0,
                    name = "Культура",
                    shortName = "Культура",
                    value = "Рапс"
                )
            )
        ),
        Task(
            id = 183,
            operation = "Посев",
            status = Task.Status.WORK,
            pole = "П108",
            date = "22.11.23",
            owner = employerOwner,
            assigned = employer,
            priority = Task.Priority.NORMAL,
            additionalInfo = "",
            params = listOf(
                TaskParam(
                    id = 0,
                    name = "Культура",
                    value = "Пшеница",
                    shortName = "Культура",
                )
            )
        ),
        Task(
            id = 184,
            operation = "Посев",
            status = Task.Status.TODO,
            pole = "П110",
            date = "25.11.23",
            owner = employerOwner,
            assigned = employer,
            priority = Task.Priority.LOW,
            additionalInfo = "",
            params = listOf(
                TaskParam(
                    id = 0,
                    name = "Культура",
                    value = "Пшеница",
                    shortName = "Культура",
                )
            )
        )
    )


    val taskFields = listOf(
        TaskField(
            id = 0,
            operationId = emptyList(),
            sortOrder = 1,
            type = TaskField.Type.OPERATION,
            desc = "Тип задачи",
            shortDesc = "",
            isRequired = true,
            customParam = TaskField.CustomParam.NONE
        ),
        TaskField(
            id = 1,
            operationId = listOf(1, 2, 3),
            sortOrder = 2,
            type = TaskField.Type.FIELD,
            desc = "Поле",
            shortDesc = "",
            isRequired = true,
            customParam = TaskField.CustomParam.NONE
        ),
        TaskField(
            id = 2,
            operationId = listOf(1, 2, 3),
            sortOrder = 11,
            type = TaskField.Type.DATE,
            desc = "Дата исполнения задачи",
            shortDesc = "Дата",
            isRequired = true,
            customParam = TaskField.CustomParam.NONE
        ),
        TaskField(
            id = 3,
            operationId = listOf(1, 2, 3),
            sortOrder = 3,
            type = TaskField.Type.CULTURE,
            desc = "Культура",
            shortDesc = "",
            isRequired = true,
            customParam = TaskField.CustomParam.NONE
        ),
        TaskField(
            id = 4,
            operationId = listOf(1, 2),
            sortOrder = 4,
            type = TaskField.Type.CUSTOM,
            desc = "Глубина, см",
            shortDesc = "Глубина",
            isRequired = true,
            customParam = TaskField.CustomParam.DEPTH
        ),
        TaskField(
            id = 5,
            operationId = listOf(1, 2, 3),
            sortOrder = 5,
            type = TaskField.Type.CUSTOM,
            desc = "Рабочая скорость, км/ч",
            shortDesc = "Cкорость",
            isRequired = true,
            customParam = TaskField.CustomParam.SPEED
        ),
        TaskField(
            id = 6,
            operationId = listOf(3),
            sortOrder = 6,
            type = TaskField.Type.CUSTOM,
            desc = "Расход рабочего растрова, л/га",
            shortDesc = "Расход",
            isRequired = true,
            customParam = TaskField.CustomParam.SOLVENT
        ),
        TaskField(
            id = 7,
            operationId = listOf(1, 2, 3),
            sortOrder = 7,
            type = TaskField.Type.CUSTOM,
            desc = "Дополнительная информация",
            shortDesc = "",
            isRequired = false,
            customParam = TaskField.CustomParam.ADDITIONAL
        ),
        TaskField(
            id = 8,
            operationId = listOf(1, 2, 3),
            sortOrder = 8,
            type = TaskField.Type.PRIORITY,
            desc = "Приоритет",
            shortDesc = "",
            isRequired = true,
            customParam = TaskField.CustomParam.NONE
        ),
        TaskField(
            id = 9,
            operationId = listOf(1, 2, 3),
            sortOrder = 10,
            type = TaskField.Type.EMPLOYEE,
            desc = "Исполнитель",
            shortDesc = "",
            isRequired = true,
            customParam = TaskField.CustomParam.NONE
        ),
    )

    val cultures = listOf(
        Culture(
            id = 0,
            title = "Рапс"
        ),
        Culture(
            id = 1,
            title = "Рож"
        ),
        Culture(
            id = 2,
            title = "Пшеница"
        ),
        Culture(
            id = 3,
            title = "Овес"
        ),
        Culture(
            id = 4,
            title = "Гречка"
        )
    )

    val fields = listOf(
        Field(
            id = 0,
            title = "А-121-АБ",
            lat = 0.0,
            lon = 0.0
        ),
        Field(
            id = 1,
            title = "А-122-АБ",
            lat = 0.0,
            lon = 0.0
        ),
        Field(
            id = 2,
            title = "А-230-АБ",
            lat = 0.0,
            lon = 0.0
        ),
    )

    val operations = listOf(
        Operation(
            id = 0,
            title = "Посев",
            param = TaskField.CustomParam.SOWING
        ),
        Operation(
            id = 1,
            title = "Обработка почвы",
            param = TaskField.CustomParam.TILLAGE
        ),
        Operation(
            id = 2,
            title = "Защита растений",
            param = TaskField.CustomParam.PROTECT
        ),
    )

    val priorities = listOf(
        Priority(
            id = 0,
            title = "Пониженный",
            code = Task.Priority.LOW
        ),
        Priority(
            id = 1,
            title = "Нормальный",
            code = Task.Priority.NORMAL
        ),
        Priority(
            id = 2,
            title = "Высокий",
            code = Task.Priority.HIGH
        ),
    )
}