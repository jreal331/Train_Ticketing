
//fitur Log-in | done crosscheck 
private void adminActionPerformed(java.awt.event.ActionEvent evt) {
    if (username.getText().equals("")  || password.getText().equal("")) {
            JOptionPane.showMessageDialog(null, "Masukkan Username dan Password", "Validasi Data", JOptionPane. INFORMATION_MESSAGE);
} else if (!"ADMIN".equals(username.getText())||!"admin".equals(password.getText())){
    JOptionPane.showMessageDialog(null, "USERNAME / PASSWORD TIDAK SESUAI", "Validasi data",
    JOptionPane.INFORMATION_MESSAGE);
} else {
    menu menuutama = new menu();
    menuutama.setVisible(true);
    this.dispose();
    }
}

//Fitur utama | done crosscheck
private void menu1ActionPerformed(java.awt.event.ActionEvent evt){
    pesantiket tiket = new pesantiket ();
    tiket.setVisible(true);
    this.dispose();
}

private void menu2Actionperformed(java.awt.event.Action evt){
    data_film datafilm = new data_film();
    datafilm.setVisible(true);
    this.dispose();
}

    private void menu3Actionperformed(java.awt.event.Action evt){
    rekap rekapjual= new rekap();
    rekapjual.setVisible(true);
    this.dispose();
}

// No 3 | Fitur-fitur pesan tiket | done crosscheck
private void btnPesanActionPerformed(java.awt.event.ActionEvent evt) {
    Connection conn=(Connection)Koneksi.BukaKoneksi();

    if(conn != null) {
        try{
            Statement stat=conn.createStatement();
            ResultSet res = stat.executeQuery("select max(right(no_transaksi,5))" +
             "as no_urut from pembelian order by no_transaksi ASC:");

            if(res.first() == false) {
                no_transaksi.setText("T-00001");
            }else{
                int no = res.gentInt("no_urut") + 1;
                String nomor = String.valueOf(no);
                int oto = nomor.length();
                for(int i = 0; i < 5-oto; i++) {
                    nomor = "0" + nomor;
                }
                no_transaki.setText("T-"+nomor);         
                no_transaki.setEditable(false);     
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }else{
        JOptionPane.showMessageDialog(null, "SQL Error : Kesalahan Pada Koneksi", "Kesalahan",
        JOptionPane.WARNING_MESSAGE);
    }
}


//Fitur menampilkan total pesanan dan seat/kursi terpilih | Done Crosscheck

private void hitungActionPerfomed(java.awt.event.ActionEvent evt) {
    int jumlah = integer.parseInt(jmltiket.getText());
    int hargatiket = Integer.parseInt(harga.getText());
    int totalharga = jumlah * hargatiket;
    total.setText(String.valueOf(totalharga));
    Seat();
}


//Fitur menyimpan pesanan ke database |done crosscheck
private void simpanActionPerfomed(java.awt.event.ActionEvent evt) {

    try{
    st = cn.createStatement();
    String sql = "INSERT INTO pembelian VALUES (' " + no_transaksi.getText() +
                                                "','" + nama_cus.getText() +
                                                "','" + judulfilm.getSelectedItem().toString() +
                                                "','" + hari.getSelectedItem().toString() +
                                                "','" + waktu.getSelectedItem().toString() +
                                                "','" + tempat.getText() +
                                                "','" + harga.getText() +
                                                "','" + total.getText() + "')";
    JOptionPane.showMessageDialog(null, "DATA TERSIMPAN", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
    st.executeUpdate(sql);
    }catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);    
    }
}


//fitur struk pembelian |done crosscheck
private void printActionPerfomed(java.awt.event.ActionEvent evt) {
    try{
        Connection conn=(Connection)Koneksi.BukaKoneksi();
        Statement stm=conn.createStatement();

        try{
            String report = ("INI DIISI PATH BUAT KE NETBEANS");
            HashMap hash = new HashMap();
            //mengambil parameter dari ireport
            hash.put("kode", no_transaksi.getText());
            JaspertReport JRpt = JasperCompileManager.compileReport(report);
            JaspertPrint JPrint = JasperFileManager.fillReport(JRpt. hash, conn);
            JasperViewer.viewReport(JPrint, false);                        
        }   catch (Exception rptexcpt) {
                System.out.println("Report Can't view because : " + rptexcpt):
        } 
        Bersih();
    } catch (Exception e) {
        System.out.println(e);
    }
}


//5. Fitur Tampilan Rekap Penjualan ||Done Crosscheck
private void TampilData(){
    try{
        st = cn. createStatement();
        rs = st.executeQuery("SELECT * FROM pembelian");

        DefaultTableModel model = new DefaultTableModel();
    model.addColumn("No Transaksi"); 
    model.addColumn("Nama Customer"); 
    model.addColumn("Judul Film"); 
    model.addColumn("Waktu"); 
    model.addColumn("Seat"); 
    model.addColumn("Harga"); 
    model.addColumn("Total");
    model.getDataVector().removeAllElements();
    model.fireTableDataChanged(); 
    model.setRowCount(0);

        while (rs.next()) {
            Object[] data = {
                rs.getString("no_transaksi"), rs.getString("nama_cus"), rs.getString("judul_film"), rs.getString("hari"), 
                rs.getString("waktu"), rs.getString("seat"), rs.getString("harga"), rs.getString("total") 
            };

            model.addRow(data);
            tabelrekap.setModel(model);

        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
    

//No 7 fitur logout ||done crosscheck
private void jButton1ActionPerfomed(java.awt.event.ActionEvent evt) {
    try{
        int jawab = JOptionPane.showConfirmDialog(null, "kamu Akan LogOut Dari Aplikasi, Lanjut? ",
         "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (jawab == 0) {
            masuk_admin login == new masuk_admin();
            login.setVisible(true);
            this.dispose();
        }
    } catch (Exception ex) {
        System.err.println(ex.getMessage());
    }
}


//No 9 Fitur Check Nama Customer ||Done Crosscheck
public static boolean isStringOnlyAlphabet(String str)
{
    return ((str != null) && (!str.equals("")) && (str.matches("^[a-zA-z ]*$")));
}

private void nama_cusActionPerfomed(java.awt.event.ActionEvent evt) {

}

private void nama_cusKeyReleased(java.awt.event.KeyEvent evt) {
    if (nama_cus.getText() != "") {
        boolean key=isStringOnlyAlphabet(nama_cus.getText());
        System.out.println(key);
        if(key!=true){
            JOptionPane.showMessageDialog(rootPane, "Nama harus diisi dengan huruf");
            nama_cus.requestFocus();
            nama_cus.setText("");
        }
    }
}
