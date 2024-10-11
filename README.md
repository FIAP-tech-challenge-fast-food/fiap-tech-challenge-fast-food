# Tech Challenge SOAT : Fastfood

## Integrantes:

- Douglas da Silva Vulcano - RM: 357602 - Discord: @.vulcanow
- Danielle Emygdio - RM: 357942 - Discord: @daniemygdio
- Kaio Alves - RM: 357921 - Discord: @kaioseven
- Vinicius Saraiva - RM: 357461 - Discord: @viniciussaraiva

## Como iniciar com Docker:

### Buildar e subir o container 
```bash
docker-compose up -d --build
```

### Subir container
```bash
docker-compose up -d
```


## Documentação de API Swagger

Execute o projeto e [clique aqui](http://localhost:8080/swagger-ui/index.html#/) para acessar a documentação.

## Diagramas
[Diagrama de Fluxo](https://miro.com/app/board/uXjVK0mga40=/?share_link_id=98957191990)

[Diagrama DDD](https://miro.com/app/board/uXjVK622Unk=/?share_link_id=942343997513)

<br>
<br>

# AWS / Kubernetes

Este documento descreve os passos e comandos necessários para criar e gerenciar um cluster Kubernetes na AWS usando o Amazon EKS (Elastic Kubernetes Service).

## Versões das Ferramentas

- **kubectl**: v1.31.0
- **Kustomize**: v5.4.2
- **AWS CLI**: aws-cli/2.17.65
- **Python**: 3.12.6

## Pré-requisitos

- **AWS CLI**: Certifique-se de que a AWS CLI esteja instalada e configurada com as credenciais apropriadas.
- **kubectl**: Instale o `kubectl` para interagir com o cluster Kubernetes.
- **UUID Generator**: O comando `uuidgen` deve estar disponível para gerar um token de requisição único.

## Obtendo Subnets para Configuração

Antes de criar um cluster EKS e um grupo de nós, você precisa identificar as subnets que serão utilizadas. Subnets são obrigatórias para conectar seus nós do EKS.

Para visualizar todas as subnets disponíveis na sua VPC, execute o comando abaixo:

```bash
aws ec2 describe-subnets --query "Subnets[*].{ID:SubnetId, VPC:VpcId, CIDR: CidrBlock, AZ:AvailabilityZone}" --output table
```

Após executar basta substituir as subnets nos comandos indicados conforme necessário.

## Criação do Cluster

1. **Criar o cluster EKS:**

   ```bash
   aws eks create-cluster \
       --name fiap-tech-challenge \
       --role-arn arn:aws:iam::581324664826:role/LabRole \
       --resources-vpc-config subnetIds=subnet-050614f94dcd289e3,subnet-0e958b3dafe94798f,subnet-0bbd0181addd4f9d4,subnet-04b1e5f4be8df1230,subnet-03fd0bd5d83b10864,endpointPublicAccess=true,endpointPrivateAccess=true,publicAccessCidrs=0.0.0.0/0 \
       --kubernetes-version 1.31 \
       --logging "clusterLogging=[{types=[api,audit,authenticator,controllerManager,scheduler],enabled=false}]" \
       --client-request-token $(uuidgen) \
       --region us-east-1
   ```

2. **Descrever o cluster criado:**

   ```bash
   aws eks describe-cluster --name fiap-tech-challenge
   ```

3. **Atualizar a configuração do kubeconfig:**

   ```bash
   aws eks update-kubeconfig --name fiap-tech-challenge --region us-east-1
   ```

## Criação do Node Group

1. **Criar um grupo de nós:**

   ```bash
   aws eks create-nodegroup \
       --cluster-name fiap-tech-challenge \
       --nodegroup-name fiap-tech-nodegroup \
       --node-role arn:aws:iam::581324664826:role/LabRole \
       --subnets subnet-050614f94dcd289e3 subnet-0e958b3dafe94798f subnet-0bbd0181addd4f9d4 subnet-04b1e5f4be8df1230 subnet-03fd0bd5d83b10864 \
       --instance-types t3.medium \
       --scaling-config minSize=1,maxSize=3,desiredSize=2
   ```

2. **Descrever o grupo de nós criado:**

   ```bash
   aws eks describe-nodegroup --cluster-name fiap-tech-challenge --nodegroup-name fiap-tech-nodegroup
   ```
3. **Listar os grupos de nós no cluster:**

   ```bash
   aws eks list-nodegroups --cluster-name fiap-tech-challenge
   ```

## Aplicando Recursos no Kubernetes

1. **Aplicar configurações de deployment e serviço:**

   > **Observação:** É recomendável aguardar até que cada recurso esteja disponível antes de prosseguir para o próximo comando.

   ```bash
   kubectl apply -f volumes/
   kubectl apply -f deployment/
   kubectl apply -f svc/
   kubectl apply -f metrics.yml
   kubectl apply -f hpa.yml
   ```

2. **Descrever todos os recursos criado:**

   ```bash
   kubectl get all
   ```

3. **Excluir todos os recursos relacionados ao aplicativo:**

   ```bash
   kubectl delete all --all
   ```

## Exclusão de Node Groups

1. **Excluir o grupo de nós:**

   ```bash
   aws eks delete-nodegroup --cluster-name fiap-tech-challenge --nodegroup-name fiap-tech-nodegroup
   ```

## Exclusão do Cluster

1. **Excluir o cluster EKS:**

   ```bash
   aws eks delete-cluster --name fiap-tech-challenge
   ```

## Conclusão

Siga os passos acima para criar e gerenciar um cluster Kubernetes na AWS usando o EKS. Para mais informações sobre EKS, consulte a [documentação da AWS](https://docs.aws.amazon.com/eks/latest/userguide/what-is-eks.html).

<br>
<br>

# Stress Test Script

Este script de shell realiza um teste de estresse enviando um número especificado de requisições HTTP para uma URL fornecida.

## Pré-requisitos

- **curl**: O script utiliza o curl para fazer as requisições HTTP. Certifique-se de que o curl está instalado em seu sistema.

## Uso

1. **Dê permissão de execução ao script (se necessário):** 
   ```bash
   chmod +x stress_test.sh
   ```
2. **Execute o script passando a URL como argumento. Você pode também especificar o   número de requisições (opcional):** 
   ```bash
   ./stress_test.sh <URL> [NUM_REQUESTS]
   ```
   - **<URL\>**: A URL que você deseja testar.
   - **[NUM_REQUESTS]**: O número de requisições a serem enviadas (padrão é 1000 se não especificado).