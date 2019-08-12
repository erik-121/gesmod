<?php 
  session_start();
  ini_set('session.cache_limiter','public');
  session_cache_limiter(false);
  $_SESSION['notCarg']="true";
  $error=$_GET['app'];
  if ($_SESSION["loggedIn"]===1){
    $location = "Location: listadoDocumentos.php";
    header($location);
    exit();  
  }
  include "templates/head.php"; 
?>
<body class="fixed-nav sticky-footer bg-tdgov-dark" id="page-top">
  <?php include "templates/nav.php"; ?>
  <div class="content-wrapper">
    <div class="container-fluid">
      <!-- Breadcrumbs-->
      <ol class="breadcrumb small">
        <li class="breadcrumb-item">
          <a href="index.php"><?php echo constant("GESTORDOCUMENTAL"); ?></a>
        </li>
        <li class="breadcrumb-item active"><?php echo constant("ACCESO"); ?></li>
      </ol>
      <div id="page-wrapper">
        <section>
          <div class="jumbotron">
            <h1 class="display-4"><?php echo constant("ACCESOCREDENCIALES"); ?></h1>
            <hr class="my-4">
            <div class="row justify-content-md-center">
              <div class="col col-lg-2">
                <img src="img/icos-acceso.png" class="img-fluid">
              </div>
              <div class="col-md-9">
                <section id="form">
                  <?php
                    if($error==="e"){
                    ?>
                      <div class="alert alert-danger" role="alert">
                          <?php echo constant("USUARIOINCORRECTO"); ?>
                      </div>
                    <?php
                    }
                  ?>
        				  <form id="form-id" action="carpeta/middleCredenciales.php" method="post">
                    <div class="form-row">
                      <p class="lead"><?php echo constant("ACCESOCREDENCIALES2"); ?></p>
                      <p class="lead"><?php echo constant("ACCESOCREDENCIALES3"); ?></p>
                    </div>
                    <!-- /.row -->
                    <div class="form-row row justify-content-md-center">
                      <div class="form-group col-lg-6">
                        <label for="nombre"><?php echo constant("NOMBREDEUSUARIO"); ?></label>
                        <div class="input-group">
                          <div class="input-group-prepend">
                            <div class="input-group-text"><i class="fa fa-user"></i></div>
                          </div>
                          <input type="text" id="usuario" name="usuario" class="form-control" placeholder="<?php echo constant("USUARIO"); ?>" required>
                        </div>
                      </div>
                      <!-- /.col -->
                    </div>
                    <!-- /.row -->
                    <div class="form-row row justify-content-md-center">
                      <div class="form-group col-lg-6">
                        <label for="dni"><?php echo constant("CONTRASEÑA"); ?></label>
                        <div class="input-group">
                          <div class="input-group-prepend">
                            <div class="input-group-text"><i class="fa fa-key"></i></div>
                          </div>
                          <input type="password" id="contrasenya" name="contrasenya" class="form-control" placeholder="<?php echo constant("CONTRASEÑA2"); ?>" required>
                        </div>
                      </div>
                      <!-- /.col -->                   
                    </div>
                    <!-- /.row -->
                    <div class="form-row">
                      <div class="form-group col-lg-12 text-center">
                        <button class="btn btn-success" type="button" onclick="redirigir();"><i class="fa fa-sign-in"></i> <?php echo constant("ACCEDER"); ?></button>
                        <button type="button" class="btn btn-secondary" onclick="window.history.back();"><i class="fa fa-history"></i><?php echo constant("VOLVER"); ?></button>
                      </div>
                    </div>
                    <!-- /.row -->
        				  </form>
                </section>
              </div>
            </div>
          </div>  
        </section>
      </div>
      <!-- /.page-wrapper -->
    </div>
    <!-- /.content-wrapper -->
          
    <?php include "templates/footer.php"; ?>
  </body>
<script type="text/javascript" src="js/miniapplet.js"></script>
  <script type="text/javascript">
    function redirigir() {
      var form=document.getElementById("form-id");
      form.submit();
    }
  </script>
