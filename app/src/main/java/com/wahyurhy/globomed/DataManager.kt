package com.wahyurhy.globomed

import android.content.ContentValues
import com.wahyurhy.globomed.GloboMedDBContract.EmployeeEntry

object DataManager {

    fun fetchAllEmployee(databaseHelper: DatabaseHelper): ArrayList<Employee> {

        val employees = ArrayList<Employee>()

        val db = databaseHelper.readableDatabase

        val columns = arrayOf(
            EmployeeEntry.COLUMN_ID,
            EmployeeEntry.COLUMN_NAME,
            EmployeeEntry.COLUMN_DOB,
            EmployeeEntry.COLUMN_DESIGNATION
        )

        val cursor = db.query(
            EmployeeEntry.TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null
        )

        val idPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_ID)
        val namePos = cursor.getColumnIndex(EmployeeEntry.COLUMN_NAME)
        val dobPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_DOB)
        val designationPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_DESIGNATION)

        while (cursor.moveToNext()){

            val id = cursor.getString(idPos)
            val name = cursor.getString(namePos)
            val dob = cursor.getLong(dobPos)
            val designation = cursor.getString(designationPos)

            employees.add(Employee(id, name, dob, designation))
        }

        cursor.close()
        return employees

    }

    fun fetchEmployee(databaseHelper: DatabaseHelper, empId: String) : Employee? {
        val db = databaseHelper.readableDatabase
        var employee: Employee? = null

        val columns = arrayOf(
            EmployeeEntry.COLUMN_NAME,
            EmployeeEntry.COLUMN_DOB,
            EmployeeEntry.COLUMN_DESIGNATION
        )

        val selection = EmployeeEntry.COLUMN_ID + " LIKE ? "

        val selectionArgs = arrayOf(empId)

        val cursor = db.query(
            EmployeeEntry.TABLE_NAME,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val namePos = cursor.getColumnIndex(EmployeeEntry.COLUMN_NAME)
        val dobPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_DOB)
        val designationPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_DESIGNATION)

        while (cursor.moveToNext()) {
            val name = cursor.getString(namePos)
            val dob = cursor.getLong(dobPos)
            val designation = cursor.getString(designationPos)

            employee = Employee(empId, name, dob, designation)
        }

        cursor.close()
        return employee
    }

    fun updateEmployee(databaseHelper: DatabaseHelper, employee: Employee) {

        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(EmployeeEntry.COLUMN_NAME, employee.name)
        values.put(EmployeeEntry.COLUMN_DESIGNATION, employee.designation)
        values.put(EmployeeEntry.COLUMN_DOB, employee.dob)

        val selection = EmployeeEntry.COLUMN_ID + " LIKE ? "

        val selectionArgs = arrayOf(employee.id)

        db.update(EmployeeEntry.TABLE_NAME, values, selection, selectionArgs)
    }

    fun deleteEmployee(databaseHelper: DatabaseHelper, empId: String) : Int {

        val db = databaseHelper.writableDatabase

        val selection = EmployeeEntry.COLUMN_ID + " LIKE ? "

        val selectionArgs = arrayOf(empId)

        return db.delete(EmployeeEntry.TABLE_NAME, selection, selectionArgs)

    }

    fun deleteAllEmployee(databaseHelper: DatabaseHelper): Int {

        val db = databaseHelper.writableDatabase
        return db.delete(EmployeeEntry.TABLE_NAME, "1", null)

    }
}