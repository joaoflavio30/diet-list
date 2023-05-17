# Diet Plan

Esse é um app a qual o usuario bota suas metas diarias(calorias,proteina,carboidrato,gordura,água) e insere as refeições a qual ele ingere durante o dia, e atraves de uma UI interativa ele vai gerindo a propria dieta

<div style="display: flex; justify-content: space-between;">
  <img src="https://github.com/joaoflavio30/diet-list/assets/111129857/6d78d044-44df-4e30-b4a6-edea1704c81f" alt="Home" style="width: 200px;">
  <img src="https://github.com/joaoflavio30/diet-list/assets/111129857/b4b6e8b8-e360-45ae-ade9-50d793a28da8" alt="Search" style="width: 200px;">
  <img src="https://github.com/joaoflavio30/diet-list/assets/111129857/99d65dbe-9e37-416a-875e-eda1dfca0203" alt="Profile" style="width: 200px;">
</div>




## Tecnologias Utilizadas


* [Retrofit]() - O framework usado para fazer requisições HTTP de forma mais simples e eficiente
* [Room]() - Para pesistencia de dados
* [Firebase Authentication]() - Usada para login e registro
* [Firebase Storage]() - Usada para armazenar na nuvem a imagem de perfil do usuario
* [Hilt]() - Para Injeção de dependências
* [Edaman Api](https://www.edamam.com/) - Api utilizada para extrair informações nutricionais de alimentos
* [Navigation Component]() - para navegação entre fragments
* [Coroutines]() - Para escrever código assincrono de forma mais simples e concisa
* [Android Image Cropper](https://github.com/CanHub/Android-Image-Cropper) - Para cortar a imagem de perfil do usuario

## Arquitetura

O aplicativo segue a arquitetura MVVM (Model-View-ViewModel), que é uma arquitetura de software que separa claramente a lógica de negócios da interface do usuário. A arquitetura consiste nos seguintes componentes:

* [Model]() - Esta camada é responsável pela abstração das fontes de dados. Model e ViewModel trabalham juntos para obter e salvar os dados.

* [View]() - Responsável pela exibição dos dados e interação com o usuário.

* [ViewModel]() - Responsável por intermediar a comunicação entre o Model e a View, fornecendo os dados necessários para a exibição na interface do usuário.

Além disso, o aplicativo utiliza os princípios do Clean Architecture, que promovem a separação de responsabilidades e a dependência da direção invertida.

## Modularização

O aplicativo é dividido em módulos para melhorar a escalabilidade, manutenção e reutilização de código. Cada módulo representa uma funcionalidade específica e pode ser desenvolvido e testado de forma independente.

Estou usando no meu app um padrão de Modularização por Camada + Recurso (Layer + Feature ou Modularização por Componente)

![image](https://github.com/joaoflavio30/diet-list/assets/111129857/ffd3e5d8-2a64-4e85-882a-d6bff58182d7)


Os módulos utilizados no aplicativo são:

* [App]() - Módulo principal do aplicativo, responsável pela inicialização e configuração global.
* [UI]() - Módulo onde esta presente a camada de UI, dentro desse módulo esta presente os modulos da camada de Presentation de cada feature do app como a view e viewModel
* [Components]() - Módulo onde esta presente a busca de dados e lógica de negócio de cada feature do app, há submódulos Domain+Data que é necessario para a features correspondentes


Cada módulo segue a estrutura básica do projeto Android e possui sua própria camada de apresentação, camada de domínio (lógica de negócios) e camada de dados (comunicação com a API, armazenamento local, etc.). A modularização permite que o código seja mais organizado, facilita a colaboração entre equipes e torna o aplicativo mais flexível para adicionar ou remover funcionalidades.

Esse padrão de modularização é interessante para este tipo de aplicativo pois o modulo de components esta totalmente desacoplado ao modulo de UI, por isso posso reutilizar um modulo de componente qualquer para qualquer modulo de UI(exemplo, posso utilizar um componente de authentication para a Camada de Ui de Perfil do usuario, sendo que a priori o componente de authentication foi feito para Camada de UI de login e registro)

Com isso,  separamos as classes que raramente mudamos como por exemplo, o que basicamente guia nosso app que são os UseCases, para as que frequentemente mudamos, minimizando assim o número de recompilações.

Como o modulo de Componente é independente ele pode compilar módulos em paralelo(ele não precisa aguaradar a compilação de nenhum módulo), com isso melhorando o tempo geral de compilação
