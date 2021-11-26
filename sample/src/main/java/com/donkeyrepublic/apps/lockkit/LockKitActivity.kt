package com.donkeyrepublic.apps.lockkit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bike.donkey.lockkit.DonkeyConfig
import bike.donkey.lockkit.DonkeyLockKit
import bike.donkey.lockkit.updates.ConnectionUpdate

class LockKitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // optionally update the config values
        DonkeyLockKit.config.apply {
            environment = DonkeyConfig.ServerEnvironment.TEST
            logLevel = DonkeyConfig.LogLevel.INFO
        }

        // initialize the sdk
        DonkeyLockKit.initializeSdk(this, SDK_TOKEN, onResult = { result ->
            result.onSuccess { println("InitializeSDK = success") }
                .onFailure { println("InitializeSDK = failure (${it.message})") }
        })

        // initialize the lock before doing any other action with it (initialize only once)
        DonkeyLockKit.initializeLock(DEVICE_NAME, KEY, PASSKEY, onResult = { result ->
            result.onSuccess { println("InitializeLock = success") }
                .onFailure { println("InitializeLock = failure (${it.message})") }
        })

        // example usage for unlocking
        DonkeyLockKit.unlock(DEVICE_NAME,
            onUpdate = { update ->
                // you can use the update description for logging (not suitable for updating UI)
                println(update.description)
                // below are relevant connection updates for unlocking
                when (update) {
                    is ConnectionUpdate.Searching -> println("Searching")
                    is ConnectionUpdate.WeakSignal -> println("WeakSignal")
                    is ConnectionUpdate.Connecting -> println("Connecting")
                    is ConnectionUpdate.Connected -> println("Connected")
                    is ConnectionUpdate.ReadCharacteristics -> println("ReadCharacteristics")
                    is ConnectionUpdate.SendingCommand -> println("SendingCommand")
                    else -> println("do nothing")
                }
            },
            onResult = { result ->
                result.onSuccess { println("Unlock = success") }
                    .onFailure { println("Unlock = failure (${it.message})") }
            }
        )

        // example usage for locking
        DonkeyLockKit.lock(DEVICE_NAME,
            onUpdate = { update ->
                // you can use the update description for logging (not suitable for updating UI)
                println(update.description)
                // below are relevant connection updates for locking
                when (update) {
                    is ConnectionUpdate.Searching -> println("Searching")
                    is ConnectionUpdate.WeakSignal -> println("WeakSignal")
                    is ConnectionUpdate.Connecting -> println("Connecting")
                    is ConnectionUpdate.Connected -> println("Connected")
                    is ConnectionUpdate.ReadCharacteristics -> println("ReadCharacteristics")
                    is ConnectionUpdate.SendingCommand -> println("SendingCommand")
                    is ConnectionUpdate.PushToLock -> println("PushToLock")
                    else -> println("do nothing")
                }
            },
            onResult = { result ->
                result.onSuccess { println("Lock = success") }
                    .onFailure { println("Lock = failure (${it.message})") }
            }
        )

        // example usage for preparing end rental
        DonkeyLockKit.prepareEndRental(DEVICE_NAME,
            onUpdate = { update ->
                // you can use the update description for logging (not suitable for updating UI)
                println(update.description)
                // below are relevant connection updates for preparing end rental
                when (update) {
                    is ConnectionUpdate.Searching -> println("Searching")
                    is ConnectionUpdate.WeakSignal -> println("WeakSignal")
                    is ConnectionUpdate.Connecting -> println("Connecting")
                    is ConnectionUpdate.Connected -> println("Connected")
                    is ConnectionUpdate.ReadCharacteristics -> println("ReadCharacteristics")
                    is ConnectionUpdate.SendingCommand -> println("SendingCommand")
                    is ConnectionUpdate.PushToLock -> println("PushToLock")
                    is ConnectionUpdate.ExtraLockCheck -> println("ExtraLockCheck")
                }
            },
            onResult = { result ->
                result.onSuccess { println("PrepareEndRental = success") }
                    .onFailure { println("PrepareEndRental = failure (${it.message})") }
            }
        )

        // remember to finalize the lock when not having any more use for it (usually after end rental on TOMP)
        DonkeyLockKit.finalizeLock(DEVICE_NAME, onResult = { result ->
            result.onSuccess { println("FinalizeLock = success") }
                .onFailure { println("FinalizeLock = failure (${it.message})") }
        })
    }

    private companion object {
        // examples of variables needed for using Donkey Lock Kit
        const val SDK_TOKEN = "yXkocTBApxQ1kKCx_ysLxspzD1v7j-9QuPHqEx5S"
        const val DEVICE_NAME = "AXA:921C148F25E092504FF7"
        const val KEY = "ea5058bfd0284739a5f57fc48e46eb911ef11998-db75ccefbd4e5cf3fbdaab80d06de0bd79fc237c-2579294585377c3e4ba5e55aca7ab6153e0fbdc0-3e0d195e2047c6932262846718ed3760ac3fd3b9-372032010d0ad6e45444cfad257e154e7a245002-402d421421c82eb42822"
        const val PASSKEY = "3be1f82915824840fba6c5ca0ca2dccab3da2c92-72eb071d79103a4d4ff096fb03821d9ad4b99280-ce95ef1e92bc383800128b40eb869bc03a626672-28b93fd6a7097d8e492bc1930817625b859dba1d-c5f4ef0049b8eb88557df7cc75ac0c4da90a34b8-8b5befe0dec962c79579855758cf222c2e63ece6-502fcef3ef719ae5d14a864b49f42b677f42dc99-330b2f1ca3e9e6e081e99d9fb50a2a345f57aa74-7dac6d1c85a5809217fcdbe6c2c6db38062fa5f8-05300fe089c0258587398d85ca57466673b61aba-9de8555ee9a0e7d021f2a6aebdbab2b390aa5a2e-5bfc24cf7d244431195e580bd44ab56f66825b0b-f18ea8e4f1a4834c249c22799fb1febc2364beea-ad88963513283fb12df7c39c2a96b967548751fd-cdcf43b7bb24c0df3498f10f240249b5d04930b6-00be52cfd47490abbc9d81700e5baa75f4cdc4da-9e3ba10f3db2f700a617afba152b5990c1c5eb53-682034ed33db7697c2ff952ecc80203aa969805a-60411ed2b1e9ef23285c6b2c11fb2c1785e9b6da-a390625f728dd21b09d0f89c757c53472a2fdc53-67bec25c0dce88cdbfea4d40538a48fde5f1d714-9ae0b429c633a0e2a57492c3b2c86312cea276d2-74957142d5ed8b3d3d0bdf962bff7a096e1e1c89-500b708fd2167fb38599b3f31c425643644c8b1e-522296b10ff1062245c827cdcb5c8279cf6f23da-dfe0a06cb0322576c3efedbccfd960064f2f15b3-2b942d22b2f5d763244f596a101ef59fe65b67cf-1a42f7122e22aa961382f36eef97013e950bc26a-cfaaa9b5f6582f304c4aa910582e1587ee452705-d020489f794b17bffcd5263444d221a11aa603a2-fd05d15087fde5b4070f9df69c7a818bd2c48435-7fcdf5ab2703adcd3ee59d09c65867ebcfec5237-ad0e0a4bcabb9ca5777fd9699e95782259b8f123-70f55fcd455cf0b90db484b731d6f0754cb7d01f-65281d80f21b1d827ddb72bf74fc693d76b77ba4-624411cbcccca17147649f0d5c5daff9b4c961e9-1d1314cc8399f91e3e3784f8a582f87db75779d1-06a7f9d2f683ab33d5392b394ed273d906e9b90b-3f8d5282f9d2981b2505abc65be648f6233b8be7-b31b6a71b5f76f75ec25fa4bbbba112bc0e19110-89282b5c8fd1792608857d2eb30ae8daba1c664d-e7a45b5ec821ca47bbf547b04016252836adca11-f8fc894bfa6b90f93286dc85fd7ca34e640ae9c4-c675a0467d71955d790dd260bf586f8315cffad2-cbbbd49d477931b124d3ef82744733044f1af716-5d1dab95acdec64b7accff832771812056b38cc6-0a495804a0f8200d98f15bb0c21b51e90d4f8a28-e94f8fe2a044ac0c8f565cd36d80de16927ab5c3-d92c4a16edc87026c1a7ac0b3a1d39d56688f73e-aaeb7f1e5a11e8eb33a4b8d1f6add64ecd0015d0-7fcd50f049e489b7d879721dd3552f3ac1eedb3e-8f4f34452da83af7db8c60ef12873a38a087da87-205bcef7712fd16fa131e1047c56e1cceb08e70b-6f45c7c7f19165957fb22e20fcd231306375e8f3-b6a8fa978c9cc3f1b78d6cc0938af0e948e4a50c-97f5cd1961f976e23e94cd78bcd56c8bc8ca0f0b-487242e0eb115db79e316d0fc82609f9ecb9d290-cf47d12a11c633de069636a04c46d5edb111b45b-bae6e00bb85d2b0638f4ac07bd48a180ec4ddded-e66d71916cf828c63accaf8a94931bfdacac2cd5-1c3c6bf867b5d771870109e9bcfc41dced07bc3e-d6a53e48f87698ec7c87756dfe133a6030260cac-5e3bedf6139c1fbe94e022f576f1e81a5fad728c-ec9ae92f821ede0640ce9749311f83853ec5a6d6-c1416809a15dc82b6046b201374d8b0eb3a09361-4762ca19348efd467860bf77720b96f7b5a6e2c4-6cc6dfa50c42259a1eb8034f0e0498f8eac00610-732c8c66aba24039560cf1988d292f09123022e1-e6474f168884e216c7829f768102349a4a829916-bc7b0015de55478b32ee483ab612f03331fe0a98-74b549da3e313c466ff5e905f7b7c6c31ff81d79-a127e7cb135f446a0a6c829e1c80e33dc3567385-26ee3f7c174478448fba47030a7149d828867cd9-796da2a3c92ab62315efe7bf224d7d999889a52f-b3c37a9fc82518c16a4f6222d4171d9d9f03daad-3362de54113f3cf0c7a7f8b757fd911d30fe3027-1a239592ff913cd2edac2ffad48f5b35a8b24f56-46e19eac97be9a392051bacfe058e9f7c6c2b85c-874430e03226d58b2e2788fd5131ba42bb347fa4-7cb6c7ab17d9a2fe8603c7a656f332a67bfca096-3f17bbdc88824959d0dbd2921c77e81cd0da9e6c-8b5ebddb1b407025c3d0d4f430bb5463d96cab88-c683710d407fe0331f66aaf5a3935eb5186b3b2e-fd559acf93162a79fdf7ef82e2f84126eb174107-a75f0e536c40ae129ec1c70a915cfe70a97c88a6-b905690aa4d6c4a22e374008960426ef17015d42-1e07667a0c0099133acd0c6ea8fb8c5c5489235b-004ff5f1514b99fbb7e50b14e3ecae297b890f20-a6bae5949d6fe2feb7ba2d187f5c6768d8e9bfd3-37ed094de40858b7a6a9ac6c9a9c917728f4cd15-590278549e0be92d06cde20fcd8cdb483f2787e1-deb602f36715523b5361f4db9ee94d57c4974bd6-26b1383f4b07b5a20b3ae848b904e3dc1c8552f7-14f0c2f2bde4d25153a19f19b9a0c17ab972e3dd-8d9f45c56ff834bac174627681be144db08375ea-dcbd673cf14d8c0c3c3d96409b9854361c34aeae-f5a3e340cf4f784760aa94c0f6229cfca7db0fcd-28a1c722fae0e4b0fdafda40ef78516ddc84ba18-c481e561df9ce2f2846ce20bfdc0d6f9a549327f-806847e97660509f14e65db2fc0eec8af7862f0f-d1ac5a06d33ade9656c197750b54bf7912197880-ec57c7b209f2083065ce5b710a524cfde2dd9534-8212162602f98dd69e411ae102b63d8fca236e72-d234c0cf4f424a55b1aa6f844904907c63447569-bd1cb3e268366f2906a6ea301d36e04ab7652559-23361b65bfc390c68e8050e1dd88a7dbf235c08d-1cc6e273e43d1c05bd7220af7155d1a8969104f5-44ecb7ea1279b75da554fdf57809280f227c5c84-3068fddef05c683b073f73173da21912368f2b80-e2d528bc3fff82aac6def0dc3b9ffa445103ddad-1e68fe5f717b0dcb9063b2b76a09072f32e49379-833b8c9ad364a05acf712734f2d4a6f483789f60-a918ff2ab407ec94d7554de5d25f6742e3c99f1b-8e1028bc78edcc93b7eb0993fd1d14ad23bfafb2-0b6b859c6b148640fa261146fcae8e7e7e4032ae-2358613595b99c4dbbe84e6d251ded7f88e3c52f-0eaf5b1972f14a1ffda7d27093a1469278abbd08-92afcfad331f50c9fc9a4398f6efa5abd89acf56-8f6acdcb3fcfe5f6a02622c56afb2d8ee37dfe8a-bed0786821067b94db4914ccaa0a120b8c9c3c54-3ee984e95f03a4536f633835dd1ea90b1d08b575-bc2399c65711c536e6bf266d313c24ced1dd1ff0-241255baaf3ed851a4191f64e84141ce1d380805-00227aabac4f24a66a78a536f1634d36381b5928-7ec02fefc47873f87b5c9233dd0b2dc017c88bf8-61c81f3470ff0c99ed5d00a7e9067c509753d5a0-dc35359f0386892de42310cb34a088523240826d-55a6288c8ce62b76f7c21dac2390195092bce0b3-cd03b5ed256eea63ee3885862a07fab669e91a4d-47d736fbfb1f31776c78b6fbb905cc5c1ea6a97d-5f37749a93dd970da2085933b97c9b7ff1ea25fe-9f499e16ec70d7bfb2221ab90cbb50248f2b7da9-4bf0b2644df364eb5f9351d7a735347535425998-60ce9c4035605d9c218a9290b195c1aee9526040-76900e71d8a179def2e0bcd07fa95850e76a8c64-234ee3406c43725777d2bbb38ccd58908c27ab92-f60742e86b0acbb7c0a7ece459cb5b58da51889e-225d5d98a00d34b68967add9084f344accd97d49-111010ed2bc618703a452df9e4f5a8dcca32203d-a6423c4c5f937a7cc8797589ad45a51bc16ec752-aeb035a68e8d0d7bca6caa53d97abe3dba5ce54c-124bac472d85d8940ba5f173b19b35e2fee787a7-3fd618f86a75018e7245dedcd986536a855ce1b8-d0e911b1d89c4f94a93476c8e8527ef46812eb52-370dcee1d7e5118693f573e867042cfbb0895c3e-e0b074fa453977522078a9a7dc1a3b4513041c8e-2cb8dcd2cfe4eb941672135ead47cc0b717adca8-bf745912a490143a6cea21bc4a308cbf1c4cdd8a-94a9b2911fa1a148465c31505e91743ef2238748-f9abc5a8eb46769a431b7ee5edc8075b16abf783-f1cc8d6f49758da54cb60647f491829c56dbb408-326131eaf2d2c2dd208ca1742953145a6af77372-9230e85f5a369367bd594f627127acfba86db473-bee30a592c9bcc59c0293640fe463db473d75ead-7cb07c372a8f8d08751b1f6f23adb11277bf1405-56946cfaf1f788090e03e7205b815aed71303d74-9c25a3790fa3b1e14d12ddbed14aa16df224b823-76574272a9c65c0f4ba15c52955097dc173a437e-15b16d6f796d2eb5e9b14df9d2145186385097bb-886cf35a966d3fd5e2e4b1da66460cc469b85f9e-02192fb5c74144ac5408206ce1bba72f15f0b2bb-1bee36d643b02ca4a4508e6b5a9d3dfe45ba9067-d3ac873398d319825902fc28b270e201025e4af5-3355fc58fc1d132977604c2ebd3d8e82caedf312-e3c946f43231c6b69d589f6f534e05a6bc32834d-9013fd8eca19e0f727abb000aa80cd0ce656634e-a4f412c42e1e4e65679a5c54ca128832e1931b8c-e6569a625186eacd51221f323cc575bbcc847bc0-5b9b44851462cf8892a98010959a8a9ff3f1d46f-8abd2d3ad466f2882affff6d91c99bb7887bfce0-1823976fc798fbc4f321159da762c397eb649c58-dc805647958dfe02839b7215eec845e76e4d87a2-c1003cbdd62866d94fb4211c14e2f3b1ece2e906-d97109d6cd30fe09506827d4c15cdc11421dd721-88cf904ec816671186c82ac721a7995cf5604598-3369db6a74ddbb078a2547db76e7022bb079c1f0-59e4d717687b83781f2c0e355e3759f1e84d9ff3-2a08e5119673576b195448da7736afbc7aa69094-116ce799c550ccfd7918cdfcb2c90aa30a9d0eef-232aa1cd5684f91becb72f0800793f6d2076dae0-f9d46bf798a6a1c97e50912caa8676765b2f14bf-e884363c2f586bc0090ea1fbd0115d5f502abc2c-e4a1fbd09d56627087a4f695c9323f2ba585ab0f-8fe4840d0339b5290f3ddd82aa8fceaa4abda5f5-37dcbd21073ee42758977d47f5f95d2d12b96df7-3d79fb9036e7f75c1e8556eb200f29d110b61a9f-b98ccef6d937d6355fce5c64d0b3dabe00439195-895c34fbfee9306cbec30d29f5eee6487a2caa75-f931c20806162af16e8d4549659a95d57219df29-b2fd9dd600e4bbcaeed7ad6e10f98c2fee112909-4419435dcd468437bc5260ac2080ed58cf6ef487-67cf7d39fb1a839c92a2cd8a6ad9f78d1404bc61-70ed7536b17e013e9a680d2ebcba40447c5b5ac1-b23ec91e0cdb58a9647a611ae990fa158d5633af-67fb6035ee9dc539c5c98d22c36b7a215705bdd2-9da70b487c3f0bcfdb8241fd5c4015958799f2ca-8cf5c37cd15564ff30c3be977144db6877247ce5-cc9e25459c9b58fc94b30079b9824857e85d704b-7dc2e8df7565cc465c67182e15c3384d39724dc5-7c84c299a96ab3a110bd8960917e3522c6c29c19-f83b3493718927a0857d672097c00a36b95c66ee-233d5676c1b58aba73d9601dae320084665b6a70-aa70bf3f51814989e2772dbee9fb2d426a5de6a5-386c009efa5bf5b7739b1edcd8d73351f9e6a04b-3f84dda9c362d1937a0ddde5653adaea45f63bc2-6bd5bd42ebe342bdaccd19e85b72d43d2b67ca08-fd84d12246ce5bc589fafe7823cae6ebeab574db-ee5b7f41b6097df0abe288de9e41a4b917943366-993d27dbd1cf7f7a8440e9d912e9bdac1363b777-66c53710fe5622b55c723a6bff9b90fafd9b5aee-c0d46e456cc9a84ec3ab6aa33e40e5b97e37a6af-321e4598e88776d6c4a42a676b17a263ff508e57-ba24a4cbd825f0fd4d5715f7ae1c29d595c5b70a-d5e5b06359c6d0032ef9ff22516f124dd6b0db06-eaf03d843630ed0d2aeff413b4acdeb78463e0d4-6cf082e689e87fee11abfb73492fa25dfc4d7b97-69fc1cfe1852ff98eb487853006df4c8e7a58524-a39fea00a8f9a821bf677f5bc594fd8dcd21955e-d6338e994c0b8d48f831d1b5a23f6ca8f26b0766-6509384d403281e8a7564704aba7c2dc87720d16-e8698fdf0f3f6cf7b312fbd74106d1cff9fd94c0-2e7022d113cc444af2dcd716351652e8da88459d-34b15cc577fc8e5df58d8e8736401a80e72a1023-e204f536be02f458333d200059f9ac340de38ee4-f4a9462dea03c29cd4fa2db0c6f0202858f46306-767164062caed7ef24f95a4bd0936bf04595c0f9-f4f972e2251c2d5a79e3f9dae16eb0f8ceca89ac-d857b053d3329bcee07141021d65bb85ca07a206-d6e793896c3e3dce110f67c08424c10866c77613-64f0365be3da28aa4978f24d0b48958468769131-15987b65a9beea654887868eb17166b5d31d4860-a470752142f6944ecc5a3aaee40bdeb36c91fa43-95476d6e06d06b0a0b95b6ec2350ba4d30349d86-f67e3a94a512230949590b51a9c0ed054ef3adeb-a5c280faa3ca6c9c98be47b288d45cfeb235de6b-fa5d97ad1d6d81017c470e855de46499fa85a5d3-b6541587cf07a5215da855dc6ff09abfb8fb8f87-dc255ddae33595a8d8af422ecf75975a71d760eb-8e35634f12b1115ca1450800116a4325ebe3f495-7c1183afe9c62cdaa3767bfd9bb11f0c25a5ccec-a5dc3a4005a2e96200a334ef2575f6336020d166-48b3795add60dcebaa495be091abe35eb0ced345-657bd62a8796a8d48eff918fb81bc9e35cbec96d-45c6baa87beede7b4cd9fe57859c34943419eeef-fb7ea08606b0afed14ce6c4ba64cc7eaeb94d73b-931c1e4dd4e7a8fac29bd14a9234ba64cdd239e8-0c10ad87cb38884bce61a0feecfe7470ba6d4a44-e5bebb4cf8b7c890b7da5a60e5a34ae3f4d62aba-ed529cbc88ded7835231038bbfab6ebc15d74daa-7409dc14ea1ac5752040bc8b342f1b3c9024a6a8-80a1ced4be2ee0988fe8468e3a90833525d1d4df-a997fa229534418cce4262b1634b1f2356419a5a-2301d5e7ed1fff0798b90edc5840b0b92b3739c5-5ad455d05024738b83306ddb3994f7b542353721-e68e20e4acbc80ae47fb02c938e3dfb97f8667d2"
    }
}