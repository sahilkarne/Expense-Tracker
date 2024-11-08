import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {
    public void insert(Expense expense) {
        String sql = "INSERT INTO expenses(amount, category, date) VALUES(?,?,?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, expense.getAmount());
            pstmt.setString(2, expense.getCategory());
            pstmt.setString(3, expense.getDate());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT id, amount, category, date FROM expenses";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Expense expense = new Expense(
                        rs.getInt("id"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        rs.getString("date")
                );
                expenses.add(expense);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return expenses;
    }

    public void delete(int id) {
        String sql = "DELETE FROM expenses WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Expense expense) {
        String sql = "UPDATE expenses SET amount = ?, category = ?, date = ? WHERE id = ?";

        try (Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, expense.getAmount());
            pstmt.setString(2, expense.getCategory());
            pstmt.setString(3, expense.getDate());
            pstmt.setInt(4, expense.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public double totalExpenses(){
        String sql = "SELECT SUM(amount) FROM expenses";
        double total = 0;
        try (Connection conn = Database.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            total = rs.getDouble(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return total;
    }

    public double categoryTotal(String category){
        String sql = "SELECT SUM(amount) FROM expenses WHERE category = ?";
        double total = 0;
        try (Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, category);
            try (ResultSet rs = pstmt.executeQuery()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return total;
    }

    public static List<String> getCategories(){
        String sql = "SELECT DISTINCT(category) FROM expenses";
        List<String> categories = new ArrayList<>();
        try (Connection conn = Database.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                categories.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categories;
    }
    
}
