const expenses = window.expenses;
const incomes = window.incomes;
const budgetGoal = window.budgetGoal;
const savingsGoal = window.savingsGoal;
let expensesDates = [], incomesDates = [], expensesAmounts = [], incomesAmounts = [];

for (let i = 0; i < expenses.length; i++) {
    expensesDates.push(new Date(expenses[i]["transactionDate"]));
    expensesAmounts.push(expenses[i]["transactionAmount"]);
}
for (let i = 0; i < incomes.length; i++) {
    incomesDates.push(new Date(incomes[i]["transactionDate"]));
    incomesAmounts.push(incomes[i]["transactionAmount"]);
}

var trace1 = {
    type: "scatter",
    // mode: "lines",
    name: 'Income',
    x: incomesDates,
    y: incomesAmounts,
    line: {color: '#17BECF'}
};
  
var trace2 = {
    type: "scatter",
    mode: "lines",
    name: 'Expense',
    x: expensesDates,
    y: expensesAmounts,
    line: {color: '#7F7F7F'}
};

var data1 = [trace2];

var layout1 = {
    title: "Expenses",
    height: 450,
    width: 600,
    xaxis: {
        autorange: true,
        range: [new Date(Math.min(...expensesDates)).getTime(), new Date(Math.max(...expensesDates)).getTime()],
        rangeselector: {buttons: [
            {
              count: 1,
              label: '1w',
              step: 'week',
              stepmode: 'backward'
            },
            {
              count: 1,
              label: '1m',
              step: 'month',
              stepmode: 'backward'
            },
            {step: 'all'}
          ]},
        rangeslider: {range: [new Date(Math.min(...expensesDates)).getTime(), new Date(Math.max(...expensesDates)).getTime()],},
        type: 'date'
      }
};
var data3 = [trace1];
var layout4 = {
    title: "Incomes",
    height: 450,
    width: 600,
    xaxis: {
        autorange: true,
        range: [new Date(Math.min(...incomesDates)).getTime(), new Date(Math.max(...incomesDates)).getTime()],
        rangeselector: {buttons: [
            {
              count: 1,
              label: '1w',
              step: 'week',
              stepmode: 'backward'
            },
            {
              count: 1,
              label: '1m',
              step: 'month',
              stepmode: 'backward'
            },
            {step: 'all'}
          ]},
        rangeslider: {range: [new Date(Math.min(...incomesDates)).getTime(), new Date(Math.max(...incomesDates)).getTime()],},
        type: 'date'
      }
};
var incomesCategory = [], incomesCategoryAmount = [], expensesCategory = [], expensesCategoryAmount = [];

expensesCategory = Array.from(new Set(expenses.map(obj => obj.transactionCategory)));
incomesCategory = Array.from(new Set(incomes.map(obj => obj.transactionCategory)));
for (let i = 0; i < expensesCategory.length; i++) {
    expensesCategoryAmount[i] = 0;
}

for (let i = 0; i < incomesCategory.length; i++) {
    incomesCategoryAmount[i] = 0;
}

for (let i = 0; i < expenses.length; i++) {
    expensesCategoryAmount[expensesCategory.indexOf(expenses[i]["transactionCategory"])] += expenses[i]["transactionAmount"];
}

for (let i = 0; i < incomes.length; i++) {
    incomesCategoryAmount[incomesCategory.indexOf(incomes[i]["transactionCategory"])] += incomes[i]["transactionAmount"];
}

var data2 = [{
    values: expensesCategoryAmount,
    labels: expensesCategory,
    type: 'pie'
}];

var layout2 = {
    height: 300,
    width: 400
};

var alreadySpent = 0;
for (let i = 0; i < expenses.length; i++) {
    const transactionDate = new Date(expenses[i]["transactionDate"]);
    if (transactionDate >= new Date(budgetGoal.startDate) && transactionDate <= new Date(budgetGoal.endDate)) {
        alreadySpent += expenses[i]["transactionAmount"];
    }
}

data4 = [{
    values: incomesCategoryAmount,
    labels: incomesCategory,
    type: 'pie' 
}]; 

var alreadySaved = 0;
for (let i = 0; i < incomes.length; i++) {
  alreadySaved += incomes[i]["transactionAmount"];
}

for (let i = 0; i < expenses.length; i++) {
  alreadySaved -= expenses[i]["transactionAmount"];
}

for (let i = 0; i < incomes.length; i++) {
    const transactionDate = new Date(incomes[i]["transactionDate"]);
    if (transactionDate >= new Date(budgetGoal.startDate) && transactionDate <= new Date(budgetGoal.endDate)) {
        alreadySpent += incomes[i]["transactionAmount"];
    }
}

var expensesBudget = [
    {
      domain: { x: [0, 1], y: [0, 1] },
      value: alreadySpent,
      title: { text: "Expenses Goal" },
      type: "indicator",
      mode: "gauge+number",
      color: "red",
      delta: { reference: 0 },
      gauge: { axis: { range: [null, budgetGoal.budgetAmount] } }
    }
  ];

  var incomesGoal = [
    {
      domain: { x: [0, 1], y: [0, 1] },
      value: alreadySaved,
      title: { text: "Savings Goal" },
      type: "indicator",
      mode: "gauge+number",
      color: "red",
      delta: { reference: 0 },
      gauge: { axis: { range: [null, savingsGoal.budgetAmount] } }
    }
  ];
  
var layout3 = { width: 450, height: 300 };
  
  
var expenseBudgetHeading = document.getElementById("expenseBudgetHeading");
var incomeBudgetHeading = document.getElementById("incomeBudgetHeading");
// expenseBudgetHeading.innerHTML = `Expense goal: €${budgetGoal.budgetAmount} between ${budgetGoal.startDate} and ${budgetGoal.endDate}`;
// incomeBudgetHeading.innerHTML = `Savings goal: €${budgetGoal.budgetAmount} between ${budgetGoal.startDate} and ${budgetGoal.endDate}`;
console.log(expensesCategory, expensesCategoryAmount);
Plotly.newPlot('expensesDiv', data1, layout1);
Plotly.newPlot('expensesCategories', data2, layout2);
Plotly.newPlot('expensesBudget', expensesBudget, layout3);
Plotly.newPlot('incomesBudget', incomesGoal, layout3);
Plotly.newPlot('incomesDiv', data3, layout4);
Plotly.newPlot('incomesCategories', data4, layout2);
